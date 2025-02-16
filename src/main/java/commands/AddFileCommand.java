package commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ui.components.SPLFileItem;
import ui.components.SPLFileManager;

import java.io.File;
import java.io.IOException;

public class AddFileCommand implements Command {

    private String fileName;

    private int counter = 0;

    public AddFileCommand(String fileName) {
        this.fileName = fileName;
    }

    public void execute() {
        try {
            File splFile = new File(SPLFileManager.getInstance().getAbsolutePathOpenProject() + File.separator + fileName + ".spl");
            if (splFile.createNewFile()) {
                System.out.println("File created: " + splFile.getName());
            } else {
                counter++;
                fileName = fileName + counter;
                execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //SPLFileManager.getInstance().
        SPLFileItem rootItem = new SPLFileItem(SPLFileManager.getInstance().getAbsolutePathOpenProject());
        rootItem.setIsDirectory(true);
        traverseDirTree(rootItem);
        SPLFileManager.getInstance().initializeItems(rootItem);
        rootItem.setExpanded(true);
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

}
