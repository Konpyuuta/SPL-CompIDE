package splprime.interpreter;

import commands.compile.CompileRequiredFileCommand;
import splprime.ast.SPLExpression;
import splprime.ast.ExprVisitor;
import splprime.ast.SPLStatement;
import splprime.ast.StmtVisitor;
import splprime.ast.expression.*;
import splprime.ast.expression.operator.BinaryOperator;
import splprime.ast.expression.operator.GroupingOperator;
import splprime.ast.expression.operator.LogicalOperator;
import splprime.ast.expression.operator.UnaryOperator;
import splprime.ast.statement.*;
import ui.components.SPLFileManager;

import java.io.File;
import java.util.List;

/**
 * Interpreter that takes the parsed AST and executes the program ..
 *
 * @author Maurice Amon
 */

public class TreeWalkInterpreter implements ExprVisitor<Object>, StmtVisitor<Void> {

    private Environment environment;

    public TreeWalkInterpreter(Environment environment) {
        this.environment = environment;
    }
    public void interpret(List<SPLStatement> statements) {
        try {
            for (SPLStatement statement : statements) {
                execute(statement);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void execute(SPLStatement stmt) {
        stmt.accept(this);
    }

    private void executeBlock(List<SPLStatement> statements, Environment environment) {
        Environment previousEnvironment = this.environment;
        this.environment = environment;
        for(SPLStatement statement : statements) {
            execute(statement);
        }
        this.environment = previousEnvironment;
    }

    private Object evaluate(SPLExpression expr) {
        Object result = expr.accept(this);
        return result;
    }

    @Override
    public Object visitAssignExpr(Assign expr) {
        Object value = evaluate(expr.value);
        return environment.put(expr.name.lexeme, value);
    }

    @Override
    public Object visitBinaryExpr(BinaryOperator expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);
        if(environment.containsKey(left)) {
            left = environment.get(left);
        }
        if(environment.containsKey(right)) {
            right = environment.get(right);
        }
        switch(expr.operator.type) {
            case NOT_EQUAL:
                return !left.equals(right);
            case EQUAL_EQUAL:
                return left.equals(right);
            case EQUAL:

            case GREATER:
                return (double)left > (double)right;
            case GREATER_EQUAL:
                return (double)left >= (double)right;
            case LESS:
                return (double)left < (double)right;
            case LESS_EQUAL:
                return (double)left <= (double)right;
            case PLUS_OPERATOR:
                if(left instanceof Double && right instanceof Double) {
                    return (double)left + (double)right;
                }
                if(left instanceof String && right instanceof String) {
                    return (String)left + (String)right;
                }
                throw new RuntimeError(expr.operator, "Operands must be numbers or strings!");
            case MINUS_OPERATOR:
                if(left instanceof Double && right instanceof Double) {
                    return (double)left - (double)right;
                }
                throw new RuntimeError(expr.operator, "Operands must be numbers!");
            default:
                return null;
        }
    }

    @Override
    public Object visitGroupingExpr(GroupingOperator expr) {
        Object eval = evaluate(expr.expression);
        return eval;
    }

    @Override
    public Object visitLiteralExpr(Literal expr) {
        Object literal = expr.value;
        return literal;
    }

    @Override
    public Object visitLogicalExpr(LogicalOperator expr) {
        Boolean left = (Boolean)evaluate(expr.left);
        Boolean right = (Boolean)evaluate(expr.right);
        switch(expr.operator.type) {
            case AND_OPERATOR:
                return left && right;
            case OR_OPERATOR:
                return left || right;
        }
        return null;
    }

    @Override
    public Object visitUnaryExpr(UnaryOperator expr) {
        Object right = evaluate(expr.right);
        switch (expr.operator.type) {
            case NOT:
                return !(Boolean)right;
            case MINUS_OPERATOR:
                return -(double)right;
        }
        return null;
    }

    @Override
    public Object visitVariableExpr(Variable expr) {
        Object value = environment.get(expr.name.lexeme);
        return value;
    }

    @Override
    public Void visitBlockStmt(Block stmt) {
        executeBlock(stmt.statements, environment);
        return null;
    }

    @Override
    public Void visitExpressionStmt(Expression stmt) {
        evaluate(stmt.expression);
        return null;
    }

    @Override
    public Void visitIfStmt(If stmt) {
        if((boolean)(evaluate(stmt.condition))) {
            execute(stmt.thenBranch);
        } else if (stmt.elseBranch != null) {
            execute(stmt.elseBranch);
        }
        return null;
    }

    @Override
    public Void visitPrintStmt(Print stmt) {
        System.out.println(stmt.expression.accept(this));
        String newLine = System.getProperty("line.separator");
        SPLOutput.getInstance().addOutputText(stmt.expression.accept(this) + newLine);
        return null;
    }

    @Override
    public Void visitVarStmt(Var stmt) {
        Object value = null;
        if(stmt.initializer != null) {
            value = evaluate(stmt.initializer);
        }

        environment.define(stmt.name.lexeme, value);
        return null;
    }

    @Override
    public Void visitWhileStmt(While stmt) {
        while((boolean)evaluate(stmt.condition)) {
            execute(stmt.body);
        }
        return null;
    }

    @Override
    public Void visitRequireStmt(Require stmt) {
        String file = (String)stmt.expression.accept(this);
        String absolutePath = SPLFileManager.getInstance().getAbsolutePathOpenProject() + File.separator + file;
        CompileRequiredFileCommand command = new CompileRequiredFileCommand(environment, absolutePath);
        command.execute();
        return null;
    }
}
