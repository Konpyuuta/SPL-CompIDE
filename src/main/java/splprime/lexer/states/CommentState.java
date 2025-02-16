package splprime.lexer.states;


import splprime.lexer.Lexer;

public class CommentState implements State {

    private Lexer lexer;

    public CommentState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void nextIndex(int c) {
        char d = lexer.getCodeFile()[c];
        switch(d) {
            case '\n':
                lexer.incrementLine();
                lexer.setState(lexer.getStartState());
                break;
            default:
                break;
        }
    }
}
