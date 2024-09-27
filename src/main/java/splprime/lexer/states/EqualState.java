package splprime.lexer.states;


import splprime.lexer.Lexer;
import splprime.lexer.TokenType;

public class EqualState implements State {

    private Lexer lexer;

    public EqualState(Lexer lexer) {
        this.lexer = lexer;
    }
    @Override
    public void nextIndex(int c) {
        char[] codeFile = lexer.getCodeFile();
        switch(codeFile[c-1]) {
            case '=':
                lexer.addToken(TokenType.EQUAL, null, c-1, c+1);
                break;
            default:
                lexer.addToken(TokenType.EQUAL_EQUAL, null, c-1, c);
                break;
        }
        lexer.setState(lexer.getStartState());
    }
}
