package splprime.ast;

public abstract class SPLStatement {

	protected Integer line;

	public Integer getLine() { return line; }

    public abstract <T> T accept(StmtVisitor<T> visitor);
}
