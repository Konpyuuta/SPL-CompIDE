package commands;

import javafx.scene.control.Tab;
import model.OpenedProjectModel;
import splprime.SplPrime;
import splprime.interpreter.SPLOutput;
import ui.MainWindow;
import ui.StyleFactory;
import ui.components.EditorTab;
import ui.components.MultiTabEditor;
import ui.components.editor.MainEditor;
import ui.components.editor.StandardEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** Command is responsible to run the program typed in the editor ..
 *
 * @author Maurice Amon
 */

public class RunCommand implements Command {

    @Override
    public void execute() {
        SPLOutput.getInstance().addObserver(MainWindow.getInstance(new StyleFactory()));
        String code = readFile(new File(OpenedProjectModel.getInstance().getProjectPath() + File.separator + OpenedProjectModel.getInstance().getMainFileName()));
        SPLOutput.getInstance().clearOutputText();
        SplPrime.run(code);

        // TODO Read the config file and get the main file
        // TODO Compile the main file ..
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    private String parseMainFileName() {
        String configPath = OpenedProjectModel.getInstance().getConfigPath();
        File file = new File(configPath);
        String mainFileName = extractMainFileName(file);

        return mainFileName;
    }

    private String extractMainFileName(File file) {
        String mainFile = "main.spl";
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] keyValue = line.split("=");
                if(keyValue[0] == "main-file" && keyValue.length == 2) {
                    mainFile = keyValue[1];
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mainFile;
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
