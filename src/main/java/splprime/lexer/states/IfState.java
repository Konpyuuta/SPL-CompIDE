package splprime.lexer.states;

import splprime.lexer.Lexer;

public class IfState implements State {

    private Lexer lexer;

    public IfState(Lexer lexer) {
        this.lexer = lexer;
    }
    @Override
    public void nextIndex(int c) {

    }
}
