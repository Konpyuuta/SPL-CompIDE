package splprime.ast;

public abstract class SPLExpression {
	public abstract <T> T accept(ExprVisitor<T> visitor);
}
