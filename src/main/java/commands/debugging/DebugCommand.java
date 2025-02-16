package commands.debugging;

import commands.Command;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.BreakpointModel;
import model.OpenedProjectModel;
import org.fxmisc.richtext.CodeArea;
import splprime.ast.SPLStatement;
import splprime.interpreter.Environment;
import splprime.interpreter.SPLOutput;
import splprime.interpreter.TreeWalkInterpreter;
import splprime.lexer.Lexer;
import splprime.parse.Parser;
import splprime.parse.TokenList;
import ui.MainWindow;
import ui.StyleFactory;
import ui.components.SPLToolBar;
import ui.components.editor.MainEditor;
import ui.components.execution.SPLDebugger;
import ui.components.execution.SPLVariable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Maurice Amon
 */

public class DebugCommand implements Command, EventHandler<KeyEvent> {

    public static ArrayList<List<SPLStatement>> statemensList = new ArrayList<>();

    private static TreeWalkInterpreter interpreter;

    private static Integer counter = 0;

    private static String code;

    public DebugCommand() {
        interpreter = new TreeWalkInterpreter(new Environment());
    }

    @Override
    public void execute() {
        SPLOutput.getInstance().addObserver(MainWindow.getInstance(new StyleFactory()));
        SPLOutput.getInstance().clearOutputText();
        ArrayList<Integer> breakpoints = BreakpointModel.getInstance().getBreakpointLineNumbers();
        Collections.sort(breakpoints);
        Integer startLine = 0;
        Integer iteration = 0;
        SPLOutput.getInstance().clearOutputText();
        code = readFile(new File(OpenedProjectModel.getInstance().getProjectPath() + File.separator + OpenedProjectModel.getInstance().getMainFileName()));
        for(Integer breakpoint : breakpoints) {
            String codeFragment = extractLines(0, breakpoint);
            System.out.println(codeFragment);
            Lexer scanner = new Lexer(codeFragment.toCharArray());
            TokenList tokens = scanner.scanTokens();
            Parser parser = new Parser(tokens);
            List<SPLStatement> statements = parser.parse();
            statemensList.add(statements);
            startLine = breakpoint + 1;
            iteration++;
            if(iteration == breakpoints.size()) {
                codeFragment = extractLines(0, countLines());
                System.out.println(codeFragment);
                scanner = new Lexer(codeFragment.toCharArray());
                tokens = scanner.scanTokens();
                parser = new Parser(tokens);
                List<SPLStatement> stms = parser.parse();
                statemensList.add(stms);
            }
        }
        if(statemensList.size() > 0) {
            ((SPLToolBar)MainWindow.getInstance(new StyleFactory()).toolBar).getDebugButton().setDisable(true);
            interpreter.interpret(statemensList.get(counter));
            actualizeTypeInspection();
            counter++;
        } else {
            Lexer scanner = new Lexer(code.toCharArray());
            TokenList tokens = scanner.scanTokens();
            Parser parser = new Parser(tokens);
            List<SPLStatement> statements = parser.parse();
            interpreter.interpret(statements);
        }

    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    private String extractLines(int startLine, int endLine) {
        int amountLines = endLine - startLine;
        int newLineCounter = 0;
        int indexCounter = 0;
        String codeFragment = "";
        char[] codeArray = code.toCharArray();
        for(int x = 0; x < codeArray.length; x++) {
            if(codeArray[x] == '\n') {
                newLineCounter++;
            }
            if(newLineCounter == amountLines) {
                indexCounter = x;
                break;
            }
        }
        codeFragment = code.substring(0, indexCounter);
        if(indexCounter < codeArray.length) {
            code = code.substring(indexCounter + 1);
        }
        return codeFragment;
    }

    private Integer countLines() {
        char[] codeArray = code.toCharArray();
        int newlineCounter = 0;
        for(int x = 0; x < codeArray.length; x++) {
            if(codeArray[x] == '\n') {
                newlineCounter++;
            }
        }
        return newlineCounter;
    }

    public static void resumeExecution() {
        while(statemensList.get(counter).size() == 0) {
            counter++;
        }
        interpreter.interpret(statemensList.get(counter));
        actualizeTypeInspection();
        counter++;
        if(counter == statemensList.size()) {
            counter = 0;
            interpreter = null;
            statemensList.clear();
            code = null;
            ((SPLToolBar)MainWindow.getInstance(new StyleFactory()).toolBar).getDebugButton().setDisable(false);
            MainWindow.getInstance(new StyleFactory()).getExecutionComponent().getDebugger().getResumeExecutionButton().setDisable(true);
        }
    }

    private static void actualizeTypeInspection() {
        SPLDebugger debugger = MainWindow.getInstance(new StyleFactory()).getExecutionComponent().getDebugger();
        ObservableList<SPLVariable> splVariables = debugger.getTypeInspectorUI().splVariables;
        splVariables.clear();
        for(Entry<String, Object> entry : interpreter.getEnvironment().entrySet()) {
            splVariables.add(new SPLVariable("var", entry.getKey(), entry.getValue().toString()));
        }
        debugger.getResumeExecutionButton().setDisable(false);
    }

    @Override
    public void handle(KeyEvent actionEvent) {
        if(actionEvent.getCode() == KeyCode.TAB) {
            interpreter.interpret(statemensList.get(counter));
            counter++;
        }
    }

    private String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
