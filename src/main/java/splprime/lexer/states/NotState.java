package splprime.lexer.states;

import splprime.lexer.Lexer;
import splprime.lexer.TokenType;

public class NotState implements State {

    private Lexer lexer;

    public NotState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {
        char d = lexer.getCodeFile()[c];
        switch (d) {
            case '=':
                lexer.addToken(TokenType.NOT_EQUAL, null, c-1, c+1);
                break;
            default:
                lexer.addToken(TokenType.NOT, null, c-1, c);
        }
        lexer.setState(lexer.getStartState());
    }
}
