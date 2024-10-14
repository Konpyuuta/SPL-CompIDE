package ui.components;

import commands.OpenFileCommand;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Maurice Amon
 */

public class SPLFileManager extends TreeView {

    public static SPLFileManager splFileManager;

    private String absolutePathOpenProject;

    private int clickCounter = 0;

    private SPLFileItem selectedItem;


    private SPLFileManager() {
        setMinWidth(150);
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
        this.setRoot(rootItem);
    }

    private void initializeAction() {
        setOnMouseClicked(mouseEvent -> {
            selectedItem = (SPLFileItem) getSelectionModel().getSelectedItem();
            if(selectedItem == null) {
                return;
            }
            clickCounter++;
            if(clickCounter == 2) {
                OpenFileCommand openFileCommand = new OpenFileCommand(selectedItem.getFile().getName(), selectedItem.getFile().getPath());
                openFileCommand.execute();
                clickCounter = 0;
                selectedItem = null;
            }
        });
    }

}
