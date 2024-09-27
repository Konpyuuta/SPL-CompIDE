package splprime.lexer.states;



import splprime.SplPrime;
import splprime.lexer.Lexer;
import splprime.lexer.TokenType;

import java.util.HashMap;
import java.util.Map;

import static splprime.lexer.TokenType.*;


/** Inititial state of the DFA ..
 *
 * @author Maurice Amon
 */

public class StartState implements State {

    private Lexer lexer;

    private static Map<Character, TokenType> singleCharTokensMap = new HashMap<>();
    static {
        singleCharTokensMap.put('{', LEFT_BRACE);
        singleCharTokensMap.put('}', RIGHT_BRACE);
        singleCharTokensMap.put('(', LEFT_PAREN);
        singleCharTokensMap.put(')', RIGHT_PAREN);
        singleCharTokensMap.put('*', MULT_OPERATOR);
        singleCharTokensMap.put('+', PLUS_OPERATOR);
        singleCharTokensMap.put('-', MINUS_OPERATOR);
        singleCharTokensMap.put(';', SEMICOLON);
    }

    public StartState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {
        char[] codeFile = lexer.getCodeFile();
        if(singleCharTokensMap.containsKey(codeFile[c])) {
            lexer.addToken(singleCharTokensMap.get(codeFile[c]), null, c, c+1);

        } else {
            handleInput(c);
        }
    }

    private void handleInput(int c) {
        char[] codeFile = lexer.getCodeFile();
        switch(codeFile[c]) {
            case '!':
                lexer.setState(lexer.getNotState());
                break;
            case '=':
                lexer.setState(lexer.getEqualState());
                break;
            case '<':
                lexer.setState(lexer.getLessThanState());
                break;
            case '>':
                lexer.setState(lexer.getMoreThanState());
                break;
            case '/':
                if(codeFile[c+1] == '/') {
                    lexer.setState(lexer.getCommentState());
                } else {
                    lexer.addToken(singleCharTokensMap.get(codeFile[c]), null, c, c+1);
                }
                break;
            case '"':
                lexer.setState(lexer.getStringState());
                break;
            case ' ':
                break;
            case '\r':
            case '\t':
            case '\n':
                lexer.incrementLine();
                break;
            default:
                if (Character.isDigit(codeFile[c])) {
                    lexer.setState(lexer.getNumState());
                } else if (Character.isLetter(codeFile[c])) {
                    lexer.setState(lexer.getIdState());
                } else {
                    SplPrime.error(lexer.getLine(), "Unexpected character: " + c);
                }
                break;
        }
    }
}