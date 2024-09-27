package splprime.lexer.states;

import splprime.lexer.KeywordMap;
import splprime.lexer.Lexer;
import splprime.lexer.TokenType;

public class IdState implements State {

    private Lexer lexer;

    private int counter = 1;

    public IdState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {
        char[] codeFile = lexer.getCodeFile();
        if(Character.isLetter(codeFile[c])) {
            counter++;
        } else {
            // Keywords are preferred over identifiers ..
            String text = lexer.extractSubstring(c - counter, c);
            TokenType type = KeywordMap.map.get(text);
            if (type == null) {
                type = TokenType.IDENTIFIER;
            }
            lexer.addToken(type, null, c-counter, c);
            counter = 1;
            lexer.setState(lexer.getStartState());
        }

    }
}
