package ui.components;

import commands.OpenFileCommand;
import commands.editor.FileManagerClickCommand;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.fxmisc.richtext.CodeArea;

import java.io.File;

/**
 *
 * @author Maurice Amon
 */

public class SPLFileManager extends TreeView {

    public static SPLFileManager splFileManager;

    private String absolutePathOpenProject;

    private int clickCounter = 0;

    private SPLFileItem selectedItem;

    private SPLFileItem rootItem;


    private SPLFileManager() {
        setMinWidth(250);
        setContextMenu(new SPLFileManagerContextMenu());
        initializeAction();
    }

    public static SPLFileManager getInstance() {
        if (splFileManager == null) {
            splFileManager = new SPLFileManager();
        }
        return splFileManager;
    }

    public void setAbsolutePathOpenProject(String absolutePath) {
        this.absolutePathOpenProject = absolutePath;
    }

    public String getAbsolutePathOpenProject() {
        return absolutePathOpenProject;
    }

    public void initializeItems(SPLFileItem rootItem) {
        this.rootItem = rootItem;
        this.setRoot(rootItem);
    }

    public SPLFileItem getRootItem() {
        return rootItem;
    }

    private void initializeAction() {
        setOnMouseClicked(new FileManagerClickCommand(this));
    }

    public void traverseDirTree(SPLFileItem rootItem) {
        if(rootItem.getFile().listFiles() == null) {
            return;
        }
        for(File file : rootItem.getFile().listFiles()) {
            SPLFileItem child = new SPLFileItem(file);
            if(file.isDirectory()) {
                // Create TreeItem with dir image ..
                child.setIsDirectory(true);
            } else {
                // Create TreeItem with file image ..
                child.setIsDirectory(false);
            }
            rootItem.getChildren().add(child);
            traverseDirTree(child);
        }
    }

}
