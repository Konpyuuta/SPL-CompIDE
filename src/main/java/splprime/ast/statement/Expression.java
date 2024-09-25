package splprime.ast.statement;

import splprime.ast.SPLExpression;
import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;

public class Expression extends SPLStatement {
	public final SPLExpression expression;

	public Expression(SPLExpression expression) {
		this.expression = expression;
	}

	@Override
	public <T> T accept(StmtVisitor<T> visitor) {
		return visitor.visitExpressionStmt(this);
	}
}
