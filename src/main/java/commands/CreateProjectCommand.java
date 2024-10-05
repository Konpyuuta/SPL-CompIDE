package commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ui.components.SPLFileItem;
import ui.components.SPLFileManager;
import ui.dialogs.CreateProjectDialog;

import java.io.File;
import java.io.IOException;

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
        createConfigFile(absolutePath + File.separator + projectName);

        SPLFileItem rootItem = new SPLFileItem(absolutePath + File.separator + projectName);
        rootItem.setIsDirectory(true);
        traverseDirTree(rootItem);
        SPLFileManager.getInstance().initializeItems(rootItem);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createConfigFile(String path) {
        try {
            File configFile = new File(path + File.separator + "config.spl");
            configFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
