package commands;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ui.components.SPLFileItem;
import ui.components.SPLFileManager;

import java.io.File;

/** Command for creating a new project ..
 *
 * @author Maurice Amon
 */
public class CreateProjectCommand implements Command {

    @Override
    public void execute() {
        File choosenDir = chooseDirectory();
        SPLFileItem rootItem = new SPLFileItem(choosenDir);
        rootItem.setIsDirectory(true);
        traverseDirTree(rootItem);
        SPLFileManager.getInstance().initializeItems(rootItem);
    }

    private File chooseDirectory() {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(stage);

        return selectedDir;
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
}
