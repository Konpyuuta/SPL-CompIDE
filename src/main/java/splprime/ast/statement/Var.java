package splprime.ast.statement;

import splprime.ast.SPLExpression;
import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;
import splprime.lexer.Token;

public class Var extends SPLStatement {
	public final Token name;
	public final SPLExpression initializer;
	
	public Var(Token name, SPLExpression initializer) {
		this.name = name;
		this.initializer = initializer;
	}

	@Override
	public <T> T accept(StmtVisitor<T> visitor) {
		return visitor.visitVarStmt(this);
	}
}
