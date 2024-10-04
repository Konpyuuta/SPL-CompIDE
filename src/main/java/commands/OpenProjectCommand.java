package commands;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ui.dialogs.ErrorMessageDialog;
import ui.components.SPLFileItem;
import ui.components.SPLFileManager;

import java.io.File;

public class OpenProjectCommand implements Command {

    @Override
    public void execute() {
        File choosenDir = chooseDirectory();
        if(isDirectorySPLProject(choosenDir)) {
            SPLFileItem rootItem = new SPLFileItem(choosenDir);
            rootItem.setIsDirectory(true);
            traverseDirTree(rootItem);
            SPLFileManager.getInstance().initializeItems(rootItem);
        } else {
            // Show error dialog ..
            ErrorMessageDialog errorMessageDialog = new ErrorMessageDialog(choosenDir.getAbsolutePath());
            errorMessageDialog.initComponents();
            errorMessageDialog.prepareView();
            errorMessageDialog.showView();
        }

    }

    private File chooseDirectory() {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(stage);

        return selectedDir;
    }

    private Boolean isDirectorySPLProject(File choosenDir) {
        for(File file : choosenDir.listFiles()) {
            if(file.getName() == "spl.config") {
                return true;
            }
        }
        return false;
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
