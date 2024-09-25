package splprime.ast;

import splprime.ast.statement.*;

public interface StmtVisitor<T> {
	T visitBlockStmt(Block stmt);

	T visitExpressionStmt(Expression stmt);

	T visitIfStmt(If stmt);

	T visitPrintStmt(Print stmt);

	T visitVarStmt(Var stmt);

	T visitWhileStmt(While stmt);
}
