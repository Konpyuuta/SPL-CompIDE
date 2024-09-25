package splprime.ast.expression;

import splprime.ast.SPLExpression;
import splprime.ast.ExprVisitor;

public class Literal extends SPLExpression {
	public final Object value;

	public Literal(Object value) {
		this.value = value;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visitLiteralExpr(this);
	}
}
