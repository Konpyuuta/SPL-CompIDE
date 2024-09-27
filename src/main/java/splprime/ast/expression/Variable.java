package splprime.ast.expression;

import splprime.ast.SPLExpression;
import splprime.ast.ExprVisitor;
import splprime.lexer.Token;

public class Variable extends SPLExpression {
	public final Token name;

	public Variable(Token name) {
		this.name = name;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visitVariableExpr(this);
	}

}
