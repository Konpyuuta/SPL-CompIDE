package splprime.ast.statement;

import splprime.ast.SPLExpression;
import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;

public class While extends SPLStatement {
	public final SPLExpression condition;
	public final SPLStatement body;
	
	public While(SPLExpression condition, SPLStatement body, Integer line) {
		this.condition = condition;
		this.body = body;
		this.line = line;
	}

	@Override
	public <T> T accept(StmtVisitor<T> visitor) {
		return visitor.visitWhileStmt(this);
	}
}
