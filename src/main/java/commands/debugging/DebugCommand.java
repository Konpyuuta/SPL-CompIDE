package commands.debugging;

import commands.Command;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.BreakpointModel;
import model.OpenedProjectModel;
import splprime.ast.SPLStatement;
import splprime.interpreter.Environment;
import splprime.interpreter.SPLOutput;
import splprime.interpreter.TreeWalkInterpreter;
import splprime.lexer.Lexer;
import splprime.parse.Parser;
import splprime.parse.TokenList;
import ui.MainWindow;
import ui.StyleFactory;
import ui.components.SPLMenuBar;
import ui.components.SPLToolBar;
import ui.components.execution.SPLDebugger;
import ui.components.execution.SPLVariable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;


public class DebugCommand implements Command, EventHandler<KeyEvent> {

    private static TreeWalkInterpreter interpreter;

    private static boolean debugging = false;

    @Override
    public void execute() {
        SPLOutput.getInstance().addObserver(MainWindow.getInstance(new StyleFactory()));
        SPLOutput.getInstance().clearOutputText();

        File mainFile = new File(
                OpenedProjectModel.getInstance().getProjectPath()
                        + File.separator
                        + OpenedProjectModel.getInstance().getMainFileName()
        );

        String code = readFile(mainFile);

        Lexer scanner = new Lexer(code.toCharArray());
        TokenList tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<SPLStatement> program = parser.parse();

        interpreter = new TreeWalkInterpreter(new Environment());
        interpreter.load(program);

        debugging = true;

        disableDebugButton(true);
        step();
    }

    public static void step() {
        if (!debugging || interpreter == null) return;

        while (true) {
            SPLStatement current = interpreter.getCurrentStatement();
            if (current == null) {
                finishDebugging();
                return;
            }

            int line = current.getLine();
            if (BreakpointModel.getInstance().getBreakpointLineNumbers().contains(line)) {
                updateInspector();
                enableResume(true);
                return;
            }

            boolean hasMore = interpreter.executeNext();
            if (!hasMore) {
                finishDebugging();
                return;
            }
        }
    }

    public static void resumeExecution() {
        if (!debugging || interpreter == null) return;

        // Execute the breakpoint line
        interpreter.executeNext();
        step();
    }

    private static void finishDebugging() {
        debugging = false;
        interpreter = null;

        disableDebugButton(false);
        enableResume(false);
    }

    private static void updateInspector() {
        SPLDebugger debugger = MainWindow.getInstance(new StyleFactory())
                .getExecutionComponent().getDebugger();

        ObservableList<SPLVariable> vars =
                debugger.getTypeInspectorUI().splVariables;

        vars.clear();

        for (Entry<String, Object> entry :
                interpreter.getEnvironment().entrySet()) {
            vars.add(new SPLVariable(
                    "var",
                    entry.getKey(),
                    String.valueOf(entry.getValue())
            ));
        }
    }

    private static void enableResume(boolean enable) {
        MainWindow.getInstance(new StyleFactory())
                .getExecutionComponent()
                .getDebugger()
                .getResumeExecutionButton()
                .setDisable(!enable);
    }

    private static void disableDebugButton(boolean disable) {
        ((SPLToolBar) MainWindow.getInstance(new StyleFactory()).toolBar)
                .getDebugButton()
                .setDisable(disable);
        ((SPLToolBar) MainWindow.getInstance(new StyleFactory()).toolBar)
                .getRunButton()
                .setDisable(disable);
        ((SPLMenuBar) MainWindow.getInstance(new StyleFactory()).menuBar)
                .getRunMenu()
                .setDisable(disable);
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            resumeExecution();
        }
    }

    @Override
    public void undo() {
    }

    private String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                sb.append(reader.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
