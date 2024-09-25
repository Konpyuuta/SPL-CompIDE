package splprime.ast;

import splprime.ast.expression.*;
import splprime.ast.expression.operator.BinaryOperator;
import splprime.ast.expression.operator.GroupingOperator;
import splprime.ast.expression.operator.LogicalOperator;
import splprime.ast.expression.operator.UnaryOperator;
import splprime.ast.statement.*;

public class AstPrinter implements ExprVisitor<String>, StmtVisitor<String> {

	private static final String TAB = "  ";
	private static final String NL = "\n";

	private int indent = 0;

	public String print(SPLExpression expr) {
		indent = 0;
		return expr.accept(this);
	}

	public String print(SPLStatement stmt) {
		indent = 0;
		return stmt.accept(this);
	}

	@Override
	public String visitBlockStmt(Block stmt) {
		String res = getIndent() + "BlockStmt" + NL;

		indent++;
		for (SPLStatement statement : stmt.statements) {
			res += statement.accept(this);
		}
		indent--;

		return res;
	}

	@Override
	public String visitExpressionStmt(Expression stmt) {
		String res = getIndent() + "ExprStmt" + NL;

		indent++;
		res += stmt.expression.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitIfStmt(If stmt) {
		String res = getIndent() + "IfStmt" + NL;

		indent++;
		res += stmt.condition.accept(this);
		res += stmt.thenBranch.accept(this);
		if (stmt.elseBranch != null) {
			res += stmt.elseBranch.accept(this);
		}
		indent--;

		return res;
	}

	@Override
	public String visitPrintStmt(Print stmt) {
		String res = getIndent() + "PrintStmt" + NL;

		indent++;
		res += stmt.expression.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitVarStmt(Var stmt) {
		String res = getIndent() + "VarDecl" + NL;

		indent++;
		res += getIndent() + stmt.name.lexeme + NL;
		res += stmt.initializer.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitWhileStmt(While stmt) {
		String res = getIndent() + "WhileStmt" + NL;

		indent++;
		res += stmt.condition.accept(this);
		res += stmt.body.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitAssignExpr(Assign expr) {
		String res = getIndent() + "AssignExpr" + NL;

		indent++;
		res += getIndent() + expr.name.lexeme + NL;
		res += expr.value.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitBinaryExpr(BinaryOperator expr) {
		String res = getIndent() + "BinaryExpr" + NL;

		indent++;
		res += expr.left.accept(this);
		res += getIndent() + expr.operator.lexeme + NL;
		res += expr.right.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitGroupingExpr(GroupingOperator expr) {
		String res = getIndent() + "GroupingExpr" + NL;

		indent++;
		res += expr.expression.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitLiteralExpr(Literal expr) {
		if (expr.value == null) {
			return getIndent() + "nil" + NL;
		} else if (expr.value instanceof String) {
			return getIndent() + "\"" + expr.value.toString() + "\"" + NL;
		}

		return getIndent() + expr.value.toString() + NL;
	}

	@Override
	public String visitLogicalExpr(LogicalOperator expr) {
		String res = getIndent() + "LogicalExpr" + NL;

		indent++;
		res += expr.left.accept(this);
		res += getIndent() + expr.operator.lexeme + NL;
		res += expr.right.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitUnaryExpr(UnaryOperator expr) {
		String res = getIndent() + "UnaryExpr" + NL;

		indent++;
		res += getIndent() + expr.operator.lexeme + NL;
		res += expr.right.accept(this);
		indent--;

		return res;
	}

	@Override
	public String visitVariableExpr(Variable expr) {
		String res = getIndent() + "VariableExpr" + NL;

		indent++;
		res += getIndent() + expr.name.lexeme + NL;
		indent--;

		return res;
	}

	private String getIndent() {
		String res = "";
		for (int i = 0; i < this.indent; i++) {
			res += TAB;
		}

		return res;
	}
}
