package splprime.lexer.states;

import splprime.lexer.Lexer;

public class WhiteSpaceState implements State {

    private Lexer lexer;

    public WhiteSpaceState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {

    }
}
