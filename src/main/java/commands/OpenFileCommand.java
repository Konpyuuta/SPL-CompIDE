package commands;

import model.OpenedFileModel;
import model.OpenedProjectModel;
import ui.components.EditorTab;
import ui.components.MultiTabEditor;
import ui.components.SPLEditor;
import ui.components.editor.MainEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 *
 * @author Maurice Amon
 */

public class OpenFileCommand implements Command {

    private String fileName;

    private String absolutePath;

    public OpenFileCommand(String fileName, String absolutePath) {
        this.fileName = fileName;
        this.absolutePath = absolutePath;
    }

    @Override
    public void execute() {
        File file = new File(absolutePath);
        if (!file.isFile()) {
            return;
        }
        if(!isFileOpened()) {
            Boolean isMainFile = false;
            String mainFile = OpenedProjectModel.getInstance().getMainFileName();
            String content = readFile(file);
            OpenedFileModel model = new OpenedFileModel(fileName, absolutePath, content);
            OpenedProjectModel.getInstance().addOpenedFile(model);
            if(mainFile.equals(fileName)) {
                isMainFile = true;
            }
            EditorTab newTab = new EditorTab(model.getId(), isMainFile);
            newTab.initializeContent();
            MultiTabEditor.getInstance().addTab(newTab);
            SPLEditor.getInstance().clear();
            SPLEditor.getInstance().setText(model.getContent());
        } else {
            EditorTab activeTab = (EditorTab) MultiTabEditor.getInstance().getTabs().stream().filter(e -> e.getText().equals(fileName)).collect(Collectors.toList()).get(0);
            MultiTabEditor.getInstance().getSelectionModel().select(activeTab);
        }
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    private Boolean isFileOpened() {
       OpenedFileModel openedFileModel = OpenedProjectModel.getInstance().getOpenedFileModelByAbsolutePath(absolutePath);
       if(openedFileModel != null) {
           return true;
       }
       return false;
    }

    private String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            if(file.isFile()) {
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    sb.append(reader.nextLine() + '\n');
                }
                reader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
