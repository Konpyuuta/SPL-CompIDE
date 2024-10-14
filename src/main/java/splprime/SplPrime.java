package splprime;

import javafx.scene.control.TextArea;
import splprime.ast.AstPrinter;
import splprime.ast.SPLStatement;
import splprime.interpreter.Environment;
import splprime.interpreter.SPLOutput;
import splprime.interpreter.TreeWalkInterpreter;
import splprime.lexer.Lexer;
import splprime.parse.Parser;
import splprime.parse.TokenList;
import splprime.lexer.Token;
import splprime.lexer.TokenType;
import ui.MainWindow;
import ui.StyleFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SplPrime {

	static boolean hadError = false;

	// Expects a single file that comprises SPL' program as argument
	public static void startApp(String[] args) {

    }

	public static void run(String source) {
		SPLOutput.getInstance().clearOutputText();
		Lexer scanner = new Lexer(source.toCharArray());
		TokenList tokens = scanner.scanTokens();
		Parser parser = new Parser(tokens);
		List<SPLStatement> statements = parser.parse();

		TreeWalkInterpreter treeWalkInterpreter = new TreeWalkInterpreter(new Environment());
		treeWalkInterpreter.interpret(statements);
	}

	public static void error(int line, String message) {
		report(line, "", message);
	}

	public static void error(Token token, String message) {
		if (token.type == TokenType.EOF) {
			report(token.line, " at end", message);
		} else {
			report(token.line, " at '" + token.lexeme + "'", message);
		}
	}

	private static void report(int line, String where, String message) {
		System.err.println("[line " + line + "] Error" + where + ": " + message);
		SPLOutput.getInstance().addOutputText("[line " + line + "] Error" + where + ": " + message);
		hadError = true;
	}

}
