package splprime.lexer.states;


import splprime.lexer.Lexer;
import splprime.lexer.TokenType;

public class NumState implements State {

    private Lexer lexer;

    private int counter = 1;

    public NumState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {
        char[] codeFile = lexer.getCodeFile();

        if(Character.isDigit(codeFile[c-1])) {
            counter++;
        } else if(codeFile[c] == '.') {
            counter++;

        }
        if(codeFile[c] != '.' && !Character.isDigit(codeFile[c])) {
            lexer.addToken(TokenType.NUMBER, Double.parseDouble(lexer.extractSubstring(c-counter, c)), c-counter, c+1);
            counter = 1;
            lexer.setState(lexer.getStartState());
        }
    }
}
