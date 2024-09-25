package splprime.ast.statement;

import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;

import java.util.List;

public class Block extends SPLStatement {
	public final List<SPLStatement> statements;
	
	public Block(List<SPLStatement> statements) {
		this.statements = statements;
	}

	@Override
	public <T> T accept(StmtVisitor<T> visitor) {
		return visitor.visitBlockStmt(this);
	}
}
