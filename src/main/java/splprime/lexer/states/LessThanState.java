package splprime.lexer.states;

import splprime.lexer.Lexer;
import splprime.lexer.TokenType;

public class LessThanState implements State {

    private Lexer lexer;

    public LessThanState(Lexer lexer) {
        this.lexer = lexer;
    }
    @Override
    public void nextIndex(int c) {
        char d = lexer.getCodeFile()[c];
        switch (d) {
            case '=':
                lexer.addToken(TokenType.LESS_EQUAL, null, c-1, c+1);
                break;
            default:
                lexer.addToken(TokenType.LESS, null, c-1, c);
        }
        lexer.setState(lexer.getStartState());
    }
}
