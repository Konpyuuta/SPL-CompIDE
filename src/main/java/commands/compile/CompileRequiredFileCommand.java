package commands.compile;

import commands.Command;
import splprime.ast.SPLStatement;
import splprime.interpreter.Environment;
import splprime.interpreter.TreeWalkInterpreter;
import splprime.lexer.Lexer;
import splprime.lexer.Token;
import splprime.parse.Parser;
import splprime.parse.TokenList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Maurice Amon
 */
public class CompileRequiredFileCommand implements Command {

    private String absolutePath;

    private Environment environment;

    public CompileRequiredFileCommand(Environment environment, String absolutePath) {
        this.absolutePath = absolutePath;
        this.environment = environment;
    }

    @Override
    public void execute() {
        File sourceFile = new File(absolutePath);
        Lexer scanner = new Lexer(readFile(sourceFile).toCharArray());
        TokenList tokens = scanner.scanTokens();
        for(Token token : tokens) {
            System.out.println("TK: " + token.toString());
        }
        Parser parser = new Parser(tokens);
        List<SPLStatement> statements = parser.parse();

        TreeWalkInterpreter treeWalkInterpreter = new TreeWalkInterpreter(environment);
        treeWalkInterpreter.interpret(statements);
    }

    private String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                sb.append('\n');
                sb.append(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
