package splprime.lexer.states;

import splprime.lexer.Lexer;

public class ErrorState implements State {

    private Lexer lexer;

    public ErrorState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {

    }
}
