package commands;

import commands.start.StartEditorCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.OpenedProjectModel;
import model.languages.Language;
import ui.MainWindow;
import ui.components.SPLFileItem;
import ui.components.SPLFileManager;
import ui.components.SPLToolBar;
import ui.dialogs.CreateProjectDialog;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/** Command for creating a new project ..
 *
 * @author Maurice Amon
 */
public class CreateProjectCommand implements Command, EventHandler<ActionEvent> {

    private CreateProjectDialog createProjectDialog;

    @Override
    public void execute() {
        createProjectDialog = new CreateProjectDialog();
        createProjectDialog.initComponents();
        createProjectDialog.prepareView();
        createProjectDialog.showView();
        createProjectDialog.initializeActions(this);
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    private void traverseDirTree(SPLFileItem root) {
        if(root.getFile().listFiles() == null) {
            return;
        }
        for(File file : root.getFile().listFiles()) {
            SPLFileItem child = new SPLFileItem(file);
            if(file.isDirectory()) {
                // Create TreeItem with dir image ..
                child.setIsDirectory(true);
            } else {
                // Create TreeItem with file image ..
                child.setIsDirectory(false);
            }
            root.getChildren().add(child);
            traverseDirTree(child);
        }
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String id = ((Button) actionEvent.getSource()).getId();
        switch (id) {
            case "create":
                createNewProject();
                ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
                break;
            case "close":
                ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
                break;
        }
    }

    private void createNewProject() {
        String absolutePath = createProjectDialog.getDirectoryField().getText();
        String projectName = createProjectDialog.getProjectNameField().getText();
        String mainFileName = createProjectDialog.getMainFileName().getText();
        createDirectory(absolutePath + File.separator + projectName);
        createMainFile(absolutePath + File.separator + projectName + File.separator + mainFileName);
        createConfigFile(absolutePath + File.separator + projectName, mainFileName);

        SPLFileItem rootItem = new SPLFileItem(absolutePath + File.separator + projectName);
        rootItem.setIsDirectory(true);
        traverseDirTree(rootItem);
        OpenedProjectModel.getInstance().setLanguage(Language.configuredLanguage("English"));
        StartEditorCommand startEditorCommand = new StartEditorCommand();
        startEditorCommand.execute();
        SPLFileManager.getInstance().initializeItems(rootItem);
        SPLFileManager.getInstance().setAbsolutePathOpenProject(absolutePath);
        OpenedProjectModel.getInstance().addObserver((SPLToolBar) MainWindow.getInstance(null).toolBar);
        OpenedProjectModel.getInstance().setProjectPath(absolutePath + File.separator + projectName);
        OpenedProjectModel.getInstance().setMainFileName(mainFileName + ".spl");
        OpenedProjectModel.getInstance().setConfigPath(absolutePath + File.separator + "config.splc");
    }

    private void createDirectory(String path) {
        File dir = new File(path);
        if (dir.exists()){
            // Handle error ..
        } else {
            dir.mkdirs();
        }
    }

    private void createMainFile(String path) {
        try {
            File mainFile = new File(path + ".spl");
            mainFile.createNewFile();
            Path filePath = Paths.get(path + ".spl");
            Files.writeString(filePath, "// Main File of the created project.", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createConfigFile(String path, String mainFileName) {
        try {
            File configFile = new File(path + File.separator + "config.splc");
            PrintWriter writer = new PrintWriter(path + File.separator + "config.splc");
            writer.println("LANGUAGE=English");
            writer.println("MAIN_FILE=" + mainFileName + ".spl");
            writer.println("DESIGN=Standard");
            writer.close();
            configFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
