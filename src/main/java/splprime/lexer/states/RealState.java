package splprime.lexer.states;

import splprime.lexer.Lexer;

public class RealState implements State {

    private Lexer lexer;

    public RealState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {

    }
}
