package splprime.ast.statement;

import splprime.ast.SPLExpression;
import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;

public class If extends SPLStatement {
	public final SPLExpression condition;
	public final SPLStatement thenBranch;
	public final SPLStatement elseBranch;

	public If(SPLExpression condition, SPLStatement thenBranch, SPLStatement elseBranch) {
		this.condition = condition;
		this.thenBranch = thenBranch;
		this.elseBranch = elseBranch;
	}

	@Override
	public <T> T accept(StmtVisitor<T> visitor) {
		return visitor.visitIfStmt(this);
	}
}
