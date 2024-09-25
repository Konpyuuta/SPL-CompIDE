package splprime.ast.expression;

import splprime.ast.SPLExpression;
import splprime.ast.ExprVisitor;
import splprime.scan.Token;

public class Assign extends SPLExpression {
	public final Token name;
	public final SPLExpression value;

	public Assign(Token name, SPLExpression value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visitAssignExpr(this);
	}
}
