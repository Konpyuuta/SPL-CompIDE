package splprime.ast.expression.operator;

import splprime.ast.SPLExpression;
import splprime.ast.ExprVisitor;
import splprime.scan.Token;

public class UnaryOperator extends SPLExpression {
	public final Token operator;
	public final SPLExpression right;

	public UnaryOperator(Token operator, SPLExpression right) {
		this.operator = operator;
		this.right = right;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visitUnaryExpr(this);
	}
}
