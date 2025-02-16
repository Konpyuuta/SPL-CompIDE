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
import splprime.lexer.Token;
import splprime.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

import static splprime.lexer.TokenType.*;

public class Parser {

	private TokenList tokens;

	public Parser(TokenList tokens) {
		this.tokens = tokens;
	}

	public List<SPLStatement> parse() {
		List<SPLStatement> statements = new ArrayList<>();
		while (!tokens.isLastToken()) {
			statements.add(declaration());
		}

		return statements;
	}

	/** Grammar rule for expressions ..
	 *
	 * @return
	 */

	private SPLStatement declaration() {
		TokenType tokenType = tokens.getCurrentToken().type;
			switch (tokenType) {
				case VAR:
					tokens.nextToken();
					return varDeclaration();
				default:
					return statement();
			}
	}

	/** Grammar rule for expressions ...
	 *
	 * @return
	 */

	private SPLExpression expression() {
		return assignment();
	}

	/**
	 * Grammar rule for statement-rule ..
	 *
	 * @return a production of the statement rule ..
	 */
	private SPLStatement statement() {
		TokenType tokenType = tokens.getCurrentToken().type;
		switch (tokenType) {
			case IF_CONDITION:
				tokens.nextToken();
				return ifStatement();
			case PRINT:
				tokens.nextToken();
				return printStatement();
			case WHILE_LOOP:
				tokens.nextToken();
				return whileStatement();
			case LEFT_BRACE:
				tokens.nextToken();
				List<SPLStatement> stmts = readBlockStatements();
				return new Block(stmts);
			case REQUIRE:
				return requireStatement();
			default:
				return expressionStatement();
		}
	}

	private SPLStatement requireStatement() {
		Integer line = tokens.getCurrentToken().line;
		tokens.consumeToken(REQUIRE);
		SPLExpression expression = new Literal(tokens.consumeToken(STRING).literal);
		tokens.consumeToken(SEMICOLON);
		return new Require(expression, line);
	}


	/** Grammar ruke for if-statements ..
	 *
	 * @return
	 */
	private SPLStatement ifStatement() {
		Integer line = tokens.getCurrentToken().line;
		tokens.consumeToken(LEFT_PAREN);
		SPLExpression condition = expression();
		tokens.consumeToken(RIGHT_PAREN);

		SPLStatement thenBranch = statement();
		SPLStatement elseBranch = null;
		if (tokens.matchCurrentToken(ELSE_CONDITION)) {
			elseBranch = statement();
		}

		return new If(condition, thenBranch, elseBranch, line);
	}

	/** Grammar rule for print statements ..
	 *
	 * @return
	 */
	private SPLStatement printStatement() {
		Integer line = tokens.getCurrentToken().line;
		SPLExpression value = expression();
		tokens.consumeToken(SEMICOLON);
		return new Print(value, line);
	}

	/** Grammar rule for VarDecl ...
	 *
	 * @return
	 */
	private SPLStatement varDeclaration() {
		Integer line = tokens.getCurrentToken().line;
		Token name = tokens.consumeToken(IDENTIFIER);

		SPLExpression initializer = null;
		if (tokens.matchCurrentToken(EQUAL)) {
			initializer = expression();
		}

		tokens.consumeToken(SEMICOLON);
		return new Var(name, initializer, line);
	}

	/** Grammar rule for while-statements ..
	 *
	 * @return
	 */
	private SPLStatement whileStatement() {
		Integer line = tokens.getCurrentToken().line;
		tokens.consumeToken(LEFT_PAREN);
		SPLExpression condition = expression();
		tokens.consumeToken(RIGHT_PAREN);
		SPLStatement body = statement();

		return new While(condition, body, line);
	}

	/** Grammar rule for expressions ..
	 *
	 * @return
	 */
	private SPLStatement expressionStatement() {
		Integer line = tokens.getCurrentToken().line;
		SPLExpression expr = expression();
		tokens.consumeToken(SEMICOLON);
		return new Expression(expr, line);
	}

	/** Grammar rule for blocks ..
	 *
	 * @return
	 */
	private List<SPLStatement> readBlockStatements() {
		List<SPLStatement> statements = new ArrayList<>();

		while (!tokens.isType(RIGHT_BRACE) && !tokens.isLastToken()) {
			statements.add(declaration());
		}

		tokens.consumeToken(RIGHT_BRACE);
		return statements;
	}

	/** Grammar rule for assignments ..
	 *
	 * @return
	 */
	private SPLExpression assignment() {
		SPLExpression expr = or();
		if (tokens.matchCurrentToken(EQUAL)) {
			Token equals = tokens.previous();
			SPLExpression value = assignment();

			if (expr instanceof Variable) {
				Token name = ((Variable) expr).name;
				return new Assign(name, value);
			}

			createErrorMessage(equals, "Invalid assignment target."); // [no-throw]
		}

		return expr;
	}

	/** Responsible for parsing an OR-expression
	 *
	 * @return
	 */
	private SPLExpression or() {
		SPLExpression expr = and();

		while (tokens.matchCurrentToken(OR_OPERATOR)) {
			Token operator = tokens.previous();
			SPLExpression right = and();
			expr = new LogicalOperator(expr, operator, right);
		}

		return expr;
	}

	/** 
	 *
	 * @return
	 */
	private SPLExpression and() {
		SPLExpression expr = equality();

		while (tokens.matchCurrentToken(AND_OPERATOR)) {
			Token operator = tokens.previous();
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
		while (tokens.matchCurrentToken(NOT_EQUAL) || tokens.matchCurrentToken(EQUAL_EQUAL)) {
			Token operator = tokens.previous();
			SPLExpression right = comparison();
			expr = new BinaryOperator(expr, operator, right);
		}

		return expr;
	}

	/** Grammar rule for comparison ..
	 *
	 * @return
	 */
	private SPLExpression comparison() {
		SPLExpression binaryOperator = term();

		while ((tokens.matchCurrentToken(GREATER) || tokens.matchCurrentToken(LESS) || tokens.matchCurrentToken(GREATER_EQUAL)
		|| tokens.matchCurrentToken(LESS_EQUAL))) {
			Token operator = tokens.previous();
			SPLExpression right = term();
			binaryOperator = new BinaryOperator(binaryOperator, operator, right);
		}

		return binaryOperator;
	}

	private SPLExpression term() {
		SPLExpression expr = factor();

		while ((tokens.matchCurrentToken(MINUS_OPERATOR) || tokens.matchCurrentToken(PLUS_OPERATOR))) {
			Token operator = tokens.previous();
			SPLExpression right = factor();
			expr = new BinaryOperator(expr, operator, right);
		}

		return expr;
	}

	private SPLExpression factor() {
		SPLExpression expr = unary();

		while ((tokens.matchCurrentToken(MULT_OPERATOR) || tokens.matchCurrentToken(DIV_OPERATOR))) {
			Token operator = tokens.previousToken();
			SPLExpression right = unary();
			expr = new BinaryOperator(expr, operator, right);
		}

		return expr;
	}

	private SPLExpression unary() {
		if ((tokens.matchCurrentToken(NOT) || tokens.matchCurrentToken(MINUS_OPERATOR))) {
			Token operator = tokens.previousToken();
			SPLExpression right = unary();
			return new UnaryOperator(operator, right);
		}

		return primary();
	}

	private SPLExpression primary() {
		TokenType tokenType = tokens.getCurrentToken().type;
		switch(tokenType) {
			case FALSE_VALUE:
				tokens.nextToken();
				return new Literal(false);
			case TRUE_VALUE:
				tokens.nextToken();
				return new Literal(true);
			case IDENTIFIER:
				tokens.nextToken();
				return new Variable(tokens.previousToken());
			case LEFT_PAREN:
				tokens.nextToken();
				SPLExpression expr = expression();
				tokens.matchCurrentToken(RIGHT_PAREN);
				return new GroupingOperator(expr);
			case NUMBER, STRING:
				tokens.nextToken();
				return new Literal(tokens.previousToken().literal);
		}
		throw createErrorMessage(tokens.getCurrentToken(), "Expect expression.");
	}

	private ParseError createErrorMessage(Token token, String message) {
		SplPrime.error(token, message);
		return new ParseError();
	}


}
