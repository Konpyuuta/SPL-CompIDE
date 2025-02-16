package splprime.ast.statement;

import splprime.ast.SPLExpression;
import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;

public class Print extends SPLStatement {
	public final SPLExpression expression;
	
	public Print(SPLExpression expression, Integer line) {
		this.expression = expression;
		this.line = line;
	}

	@Override
	public <T> T accept(StmtVisitor<T> visitor) {
		return visitor.visitPrintStmt(this);
	}
}
