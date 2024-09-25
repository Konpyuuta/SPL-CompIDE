package splprime;

import javafx.scene.control.TextArea;
import splprime.ast.AstPrinter;
import splprime.ast.SPLStatement;
import splprime.interpreter.Environment;
import splprime.interpreter.TreeWalkInterpreter;
import splprime.parse.Parser;
import splprime.scan.Scanner;
import splprime.scan.Token;
import splprime.scan.TokenType;
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

        try {
            runFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

	private static void runFile() throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\logik\\Documents\\StateScanner\\Fibonacci.spl"));
		run(new String(bytes, Charset.defaultCharset()));

		// Indicate an error in the exit code.
		if (hadError) {
			System.exit(65);
		}
	}

	public static void run(String source) {
		MainWindow.getInstance(null).output.clear();
		Scanner scanner = new Scanner(source);
		List<Token> tokens = scanner.scanTokens();

//		// For now, just print the tokens
//		System.out.println("============================== Tokens:");
//		for (Token token : tokens) {
//			System.out.println(token);
//		}

		Parser parser = new Parser(tokens);
		List<SPLStatement> statements = parser.parse();

		// For now, just print the top-level statements using our AstVisitor
		System.out.println("\n============================== Top-level Statements:");
		for (SPLStatement stmt : statements) {
			System.out.println(new AstPrinter().print(stmt));
		}
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
		TextArea output = MainWindow.getInstance(new StyleFactory()).output;
		output.setText(output.getText() + "[line " + line + "] Error" + where + ": " + message);
		hadError = true;
	}

}
