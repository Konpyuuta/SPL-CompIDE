package splprime.lexer.states;

import splprime.lexer.Lexer;
import splprime.lexer.TokenType;

public class StringState implements State {

    private Lexer lexer;

    private int counter = 0;

    public StringState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {
        char[] codeFile = lexer.getCodeFile();
        switch (codeFile[c]) {
            case '\n':
                lexer.incrementLine();
                break;
            case '"':
                extractString(c);
                lexer.setState(lexer.getStartState());
                counter = 0;
                break;
            default:
                counter++;
        }
    }

    private void extractString(int c) {
        StringBuilder sb = new StringBuilder();
        for (int i = c-counter; i < c; i++) {
            sb.append(lexer.getCodeFile()[i]);
        }
        System.out.println("String: " + sb.toString());
        lexer.addToken(TokenType.STRING, sb.toString(), c-counter, c);
    }
}
