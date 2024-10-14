package splprime.lexer;

public enum TokenType {
	// Inclusion of other .spl files ..
	REQUIRE,
	// Supported datatypes in SPL-Prime ..
	VAR, BOOLEAN, NUMBER, STRING, IDENTIFIER,
	// Boolean values ..
	TRUE_VALUE, FALSE_VALUE,
	// Supported Logical Operators ..
	AND_OPERATOR, OR_OPERATOR,
	// Supported Control-structures ..
	IF_CONDITION, ELSE_CONDITION, WHILE_LOOP,
	// Supported Arithmetic Operators ..
	PLUS_OPERATOR, MINUS_OPERATOR, MULT_OPERATOR, DIV_OPERATOR,
	// Other Tokens ..
	SEMICOLON, LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, NOT,
	NOT_EQUAL, EQUAL, EQUAL_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
	PRINT, EOF;
}
