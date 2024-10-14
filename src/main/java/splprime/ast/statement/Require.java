package splprime.ast.statement;

import splprime.ast.SPLExpression;
import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;

public class Require extends SPLStatement {

    public final SPLExpression expression;

    public Require(SPLExpression expression) {
        this.expression = expression;
    }
    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visitRequireStmt(this);
    }
}
