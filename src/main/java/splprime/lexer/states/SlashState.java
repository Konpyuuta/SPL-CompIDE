package splprime.lexer.states;

import splprime.lexer.Lexer;

public class SlashState implements State {

    private Lexer lexer;

    public SlashState(Lexer lexer) {
        this.lexer = lexer;
    }
    @Override
    public void nextIndex(int c) {

    }
}
