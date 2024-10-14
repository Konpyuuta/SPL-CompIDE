package splprime.lexer;

import splprime.lexer.states.*;
import splprime.parse.TokenList;

/** Lexer for SPL', reads the source file and generates a list of tokens ..
 *
 *
 * @author Maurice Amon
 */

public class Lexer {

		// In and output
		private char[] codeFile;
		private final TokenList tokens = new TokenList();

		private State currentState = new StartState(this);
		private State commentState = new CommentState(this);
		private State equalState = new EqualState(this);
		private State idState = new IdState(this);
		private State ifState = new IfState(this);
		private State lessThanState = new LessThanState(this);
		private State moreThanState = new MoreThanState(this);
		private State notState = new NotState(this);
		private State numState = new NumState(this);
		private State realState = new RealState(this);
		private State singleSignOperatorState = new SingleSignOperatorState(this);
		private State slashState = new SlashState(this);
		private State stringState = new StringState(this);
		private State startState = new StartState(this);
		// Scan state
		private int start = 0;
		private int current = 0;

	private int line = 1;

	public char[] getCodeFile() {
		return codeFile;
	}

	public Lexer(char[] codeFile) {
		this.codeFile = codeFile;
	}

	public TokenList scanTokens() {
		// We are at the beginning of the next lexeme.
		start = current;
		scanCodeFile();

		tokens.add(new Token(TokenType.EOF, "", null, line));
		return tokens;
	}

	public void scanCodeFile() {
		for(int i = 0; i < codeFile.length; i++) {
			currentState.nextIndex(i);
		}
	}

	public void setState(State currentState) {
			this.currentState = currentState;
		}

	public State getState() {
		return currentState;
	}

	public String extractSubstring(int start, int current) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < current; i++) {
			sb.append(codeFile[i]);
		}
		return sb.toString();
	}

	public void addToken(TokenType type, Object literal, int start, int end) {
		String text = extractSubstring(start, end);
		tokens.add(new Token(type, text, literal, line));
	}

	public void incrementLine() {
		line++;
	}

	public int getLine() {
		return line;
	}

	public State getLessThanState() {
		return lessThanState;
	}

	public State getIfState() {
		return ifState;
	}

	public State getIdState() {
		return idState;
	}

	public State getEqualState() {
		return equalState;
	}

	public State getCommentState() {
		return commentState;
	}

	public State getCurrentState() {
		return currentState;
	}

	public State getMoreThanState() {
		return moreThanState;
	}

	public State getNotState() {
		return notState;
	}

	public State getNumState() {
		return numState;
	}

	public State getRealState() {
		return realState;
	}

	public State getSingleSignOperatorState() {
		return singleSignOperatorState;
	}

	public State getSlashState() {
		return slashState;
	}

	public State getStartState() {
		return startState;
	}

	public State getStringState() {
		return stringState;
	}

}
