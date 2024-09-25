package splprime.ast;

import splprime.ast.expression.*;
import splprime.ast.expression.operator.BinaryOperator;
import splprime.ast.expression.operator.GroupingOperator;
import splprime.ast.expression.operator.LogicalOperator;
import splprime.ast.expression.operator.UnaryOperator;

public interface ExprVisitor<T> {
	T visitAssignExpr(Assign expr);

	T visitBinaryExpr(BinaryOperator expr);

	T visitGroupingExpr(GroupingOperator expr);

	T visitLiteralExpr(Literal expr);

	T visitLogicalExpr(LogicalOperator expr);

	T visitUnaryExpr(UnaryOperator expr);

	T visitVariableExpr(Variable expr);
}
