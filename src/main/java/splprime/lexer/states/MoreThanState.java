package splprime.lexer.states;

import splprime.lexer.Lexer;
import splprime.lexer.TokenType;

public class MoreThanState implements State {

    private Lexer lexer;

    public MoreThanState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {
        char d = lexer.getCodeFile()[c];
        switch (d) {
            case '=':
                lexer.addToken(TokenType.GREATER_EQUAL, null, c-1, c+1);
                break;
            default:
                lexer.addToken(TokenType.GREATER, null, c-1, c);
        }
        lexer.setState(lexer.getStartState());
    }
}
