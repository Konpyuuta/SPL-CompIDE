package splprime.ast.expression.operator;

import splprime.ast.SPLExpression;
import splprime.ast.ExprVisitor;
import splprime.lexer.Token;

public class BinaryOperator extends SPLExpression {
	public final SPLExpression left;
	public final Token operator;
	public final SPLExpression right;

	public BinaryOperator(SPLExpression left, Token operator, SPLExpression right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visitBinaryExpr(this);
	}

}
