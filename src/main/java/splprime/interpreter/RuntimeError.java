package splprime.interpreter;

import splprime.scan.Token;

public class RuntimeError extends RuntimeException {

    public RuntimeError(Token token, String message) {
        super(token.type.name() + " " + message);
    }
}
