package splprime.interpreter;

import splprime.lexer.Token;

public class RuntimeError extends RuntimeException {

    public RuntimeError(Token token, String message) {
        super(token.type.name() + " " + message);
    }
}
