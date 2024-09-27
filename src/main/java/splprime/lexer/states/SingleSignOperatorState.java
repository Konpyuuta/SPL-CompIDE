package splprime.lexer.states;

import splprime.lexer.Lexer;

/** This state comprises all operators that consists of single signs, namely:
 * '{', '}', '(', ')', '*', '-', '+', ';', ' ', '\n', '\r', '\t'
 *
 * @see State
 *
 * @author Maurice Amon
 *
 */
public class SingleSignOperatorState implements State {

    private Lexer lexer;

    public SingleSignOperatorState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {

    }
}
