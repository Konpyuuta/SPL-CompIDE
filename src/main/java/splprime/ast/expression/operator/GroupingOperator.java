package splprime.ast.expression.operator;

import splprime.ast.SPLExpression;
import splprime.ast.ExprVisitor;

public class GroupingOperator extends SPLExpression {
	public final SPLExpression expression;

	public GroupingOperator(SPLExpression expression) {
		this.expression = expression;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visitGroupingExpr(this);
	}
}
