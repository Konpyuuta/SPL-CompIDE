package splprime.ast;

public abstract class SPLStatement {
	public abstract <T> T accept(StmtVisitor<T> visitor);
}
