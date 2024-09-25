package splprime.scan;

import splprime.SplPrime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static splprime.scan.TokenType.*;

public class Scanner {
	// Keyword-map
	private static final Map<String, TokenType> keywords;
	static {
		keywords = new HashMap<>();
		keywords.put("and", AND);
		keywords.put("else", ELSE);
		keywords.put("false", FALSE);
		keywords.put("if", IF);
		keywords.put("or", OR);
		keywords.put("print", PRINT);
		keywords.put("true", TRUE);
		keywords.put("var", VAR);
		keywords.put("while", WHILE);
	}

	// In and output
	private final String source;
	private final List<Token> tokens = new ArrayList<>();

	// Scan state
	private int start = 0;
	private int current = 0;
	private int line = 1;

	public Scanner(String source) {
		this.source = source;
	}

	// Scan tokens
	public List<Token> scanTokens() {
		while (!isAtEnd()) {
			// We are at the beginning of the next lexeme.
			start = current;
			scanToken();
		}

		tokens.add(new Token(EOF, "", null, line));
		return tokens;
	}

	// Scan token
	private void scanToken() {
		char c = advance();
		switch (c) {
		// single-char-tokens
		case '(':
			addToken(LEFT_PAREN);
			break;
		case ')':
			addToken(RIGHT_PAREN);
			break;
		case '{':
			addToken(LEFT_BRACE);
			break;
		case '}':
			addToken(RIGHT_BRACE);
			break;
		case '-':
			addToken(MINUS);
			break;
		case '+':
			addToken(PLUS);
			break;
		case ';':
			addToken(SEMICOLON);
			break;
		case '*':
			addToken(MULT);
			break;
		// two-char-tokens
		case '!':
			addToken(match('=') ? NOT_EQUAL : NOT);
			break;
		case '=':
			addToken(match('=') ? EQUAL_EQUAL : EQUAL);
			break;
		case '<':
			addToken(match('=') ? LESS_EQUAL : LESS);
			break;
		case '>':
			addToken(match('=') ? GREATER_EQUAL : GREATER);
			break;
		case '/':
			if (match('/')) {
				// A comment goes until the end of the line.
				while (peek() != '\n' && !isAtEnd())
					advance();
			} else {
				addToken(DIV);
			}
			break;
		// whitespace
		case ' ':
		case '\r':
		case '\t':
			// Ignore whitespace.
			break;
		case '\n':
			line++;
			break;
		case '"':
			string();
			break;

		default:
			if (isDigit(c)) {
				number();
			} else if (isAlpha(c)) {
				identifier();
			} else {
				SplPrime.error(line, "Unexpected character: " + c);
			}
			break;
		}
	}

	private void identifier() {
		while (isAlphaNumeric(peek())) {
			advance();
		}

		// Prefer keyword over identifier
		String text = source.substring(start, current);
		TokenType type = keywords.get(text);
		if (type == null) {
			type = IDENTIFIER;
		}
		addToken(type);
	}

	private void number() {
		while (isDigit(peek())) {
			advance();
		}

		// Look for a fractional part.
		if (peek() == '.') {
			// Consume the "."
			advance();

			while (isDigit(peek())) {
				advance();
			}
		}

		addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
	}

	private void string() {
		while (peek() != '"' && !isAtEnd()) {
			if (peek() == '\n') {
				line++;
			}
			advance();
		}

		if (isAtEnd()) {
			SplPrime.error(line, "Unterminated string.");
			return;
		}

		// The closing "
		advance();

		// Trim the surrounding quotes.
		String value = source.substring(start + 1, current - 1);
		addToken(STRING, value);
	}

	private char advance() {
		return source.charAt(current++);
	}

	private boolean match(char expected) {
		if (isAtEnd())
			return false;
		if (source.charAt(current) != expected)
			return false;

		current++;
		return true;
	}

	private char peek() {
		if (isAtEnd())
			return '\0';
		return source.charAt(current);
	}

	private boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	private boolean isAlphaNumeric(char c) {
		return isAlpha(c) || isDigit(c);
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private boolean isAtEnd() {
		return current >= source.length();
	}

	private void addToken(TokenType type) {
		addToken(type, null);
	}

	private void addToken(TokenType type, Object literal) {
		String text = source.substring(start, current);
		tokens.add(new Token(type, text, literal, line));
	}
}
