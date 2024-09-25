package splprime.parse;

import splprime.SplPrime;
import splprime.ast.SPLExpression;
import splprime.ast.SPLStatement;
import splprime.ast.expression.*;
import splprime.ast.expression.operator.BinaryOperator;
import splprime.ast.expression.operator.GroupingOperator;
import splprime.ast.expression.operator.LogicalOperator;
import splprime.ast.expression.operator.UnaryOperator;
import splprime.ast.statement.*;
import splprime.scan.Token;
import splprime.scan.TokenType;

import java.util.ArrayList;
import java.util.List;

import static splprime.scan.TokenType.*;

public class Parser {

	private List<Token> tokens;
	private int currentToken = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<SPLStatement> parse() {
		List<SPLStatement> statements = new ArrayList<>();
		while (!isAtEnd()) {
			statements.add(declaration());
		}

		return statements;
	}

	private SPLStatement declaration() {
		try {
			if (match(VAR)) {
				return varDeclaration();
			}
			return statement();
		} catch (ParseError error) {
			synchronize();
			return null;
		}
	}

	private SPLExpression expression() {
		return assignment();
	}

	// < Statements and State declaration

	// > Statements and State parse-statement
	private SPLStatement statement() {
		// > Control Flow match-if
		if (match(IF))
			return ifStatement();
		// < Control Flow match-if
		if (match(PRINT))
			return printStatement();
		// > Control Flow match-while
		if (match(WHILE))
			return whileStatement();
		// < Control Flow match-while
		// > parse-block
		if (match(LEFT_BRACE))
			return new Block(block());
		// < parse-block

		return expressionStatement();
	}

	// < Statements and State parse-statement
	// > Control Flow if-statement
	private SPLStatement ifStatement() {
		consume(LEFT_PAREN, "Expect '(' after 'if'.");
		SPLExpression condition = expression();
		consume(RIGHT_PAREN, "Expect ')' after if condition."); // [parens]

		SPLStatement thenBranch = statement();
		SPLStatement elseBranch = null;
		if (match(ELSE)) {
			elseBranch = statement();
		}

		return new If(condition, thenBranch, elseBranch);
	}

	// < Control Flow if-statement
	// > Statements and State parse-print-statement
	private SPLStatement printStatement() {
		SPLExpression value = expression();
		consume(SEMICOLON, "Expect ';' after value.");
		return new Print(value);
	}

	// < Statements and State parse-print-statement

	// > Statements and State parse-var-declaration
	private SPLStatement varDeclaration() {
		Token name = consume(IDENTIFIER, "Expect variable name.");

		SPLExpression initializer = null;
		if (match(EQUAL)) {
			initializer = expression();
		}

		consume(SEMICOLON, "Expect ';' after variable declaration.");
		return new Var(name, initializer);
	}

	// < Statements and State parse-var-declaration
	// > Control Flow while-statement
	private SPLStatement whileStatement() {
		consume(LEFT_PAREN, "Expect '(' after 'while'.");
		SPLExpression condition = expression();
		consume(RIGHT_PAREN, "Expect ')' after condition.");
		SPLStatement body = statement();

		return new While(condition, body);
	}

	// < Control Flow while-statement
	// > Statements and State parse-expression-statement
	private SPLStatement expressionStatement() {
		SPLExpression expr = expression();
		consume(SEMICOLON, "Expect ';' after expression.");
		return new Expression(expr);
	}

	// > Statements and State block
	private List<SPLStatement> block() {
		List<SPLStatement> statements = new ArrayList<>();

		while (!check(RIGHT_BRACE) && !isAtEnd()) {
			statements.add(declaration());
		}

		consume(RIGHT_BRACE, "Expect '}' after block.");
		return statements;
	}

	// < Statements and State block
	// > Statements and State parse-assignment
	private SPLExpression assignment() {
		/*
		 * Statements and State parse-assignment < Control Flow or-in-assignment SPLExpression
		 * expr = equality();
		 */
		// > Control Flow or-in-assignment
		SPLExpression expr = or();
		// < Control Flow or-in-assignment

		if (match(EQUAL)) {
			Token equals = previous();
			SPLExpression value = assignment();

			if (expr instanceof Variable) {
				Token name = ((Variable) expr).name;
				return new Assign(name, value);
			}

			error(equals, "Invalid assignment target."); // [no-throw]
		}

		return expr;
	}

	/** Responsible for parsing an OR-expression
	 *
	 * @return
	 */
	private SPLExpression or() {
		SPLExpression expr = and();

		while (match(OR)) {
			Token operator = previous();
			SPLExpression right = and();
			expr = new LogicalOperator(expr, operator, right);
		}

		return expr;
	}

	// < Control Flow or
	// > Control Flow and
	private SPLExpression and() {
		SPLExpression expr = equality();

		while (match(AND)) {
			Token operator = previous();
			SPLExpression right = equality();
			expr = new LogicalOperator(expr, operator, right);
		}

		return expr;
	}

	/** Parses a comparison expression ..
	 *
	 * @return
	 */
	private SPLExpression equality() {
		SPLExpression expr = comparison();

		while (match(NOT_EQUAL, EQUAL_EQUAL)) {
			Token operator = previous();
			SPLExpression right = comparison();
			expr = new BinaryOperator(expr, operator, right);
		}

		return expr;
	}

	/**
	 *
	 * @return
	 */
	private SPLExpression comparison() {
		SPLExpression expr = term();

		while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
			Token operator = previous();
			SPLExpression right = term();
			expr = new BinaryOperator(expr, operator, right);
		}

		return expr;
	}

	// < comparison
	// > term
	private SPLExpression term() {
		SPLExpression expr = factor();

		while (match(MINUS, PLUS)) {
			Token operator = previous();
			SPLExpression right = factor();
			expr = new BinaryOperator(expr, operator, right);
		}

		return expr;
	}

	// < term
	// > factor
	private SPLExpression factor() {
		SPLExpression expr = unary();

		while (match(DIV, MULT)) {
			Token operator = previous();
			SPLExpression right = unary();
			expr = new BinaryOperator(expr, operator, right);
		}

		return expr;
	}

	// < factor
	// > unary
	private SPLExpression unary() {
		if (match(NOT, MINUS)) {
			Token operator = previous();
			SPLExpression right = unary();
			return new UnaryOperator(operator, right);
		}

		return primary();
	}

	// < unary

	// > primary
	private SPLExpression primary() {
		if (match(FALSE))
			return new Literal(false);
		if (match(TRUE))
			return new Literal(true);

		if (match(NUMBER, STRING)) {
			return new Literal(previous().literal);
		}
		// > Inheritance parse-super

		// > Statements and State parse-identifier

		if (match(IDENTIFIER)) {
			return new Variable(previous());
		}
		// < Statements and State parse-identifier

		if (match(LEFT_PAREN)) {
			SPLExpression expr = expression();
			consume(RIGHT_PAREN, "Expect ')' after expression.");
			return new GroupingOperator(expr);
		}
		// > primary-error

		throw error(peek(), "Expect expression.");
		// < primary-error
	}

	private boolean match(TokenType... types) {
		for (TokenType type : types) {
			if (check(type)) {
				advance();
				return true;
			}
		}

		return false;
	}

	private Token consume(TokenType type, String message) {
		if (check(type))
			return advance();

		throw error(peek(), message);
	}

	private boolean check(TokenType type) {
		if (isAtEnd())
			return false;
		return peek().type == type;
	}

	private Token advance() {
		if (!isAtEnd())
			currentToken++;
		return previous();
	}

	private boolean isAtEnd() {
		return peek().type == EOF;
	}

	private Token peek() {
		return tokens.get(currentToken);
	}

	private Token previous() {
		return tokens.get(currentToken - 1);
	}

	private ParseError error(Token token, String message) {
		SplPrime.error(token, message);
		return new ParseError();
	}

	private void synchronize() {
		advance();

		while (!isAtEnd()) {
			if (previous().type == SEMICOLON)
				return;

			switch (peek().type) {
			case VAR:
			case IF:
			case WHILE:
			case PRINT:
				return;
			}

			advance();
		}
	}
}
