package splprime.ast.statement;

import splprime.ast.SPLExpression;
import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;

public class While extends SPLStatement {
	public final SPLExpression condition;
	public final SPLStatement body;
	
	public While(SPLExpression condition, SPLStatement body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public <T> T accept(StmtVisitor<T> visitor) {
		return visitor.visitWhileStmt(this);
	}
}
