package ui.components;

import commands.Actions;
import commands.SPLCommandClient;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import model.OpenedProjectModel;

public class SPLFileManagerContextMenu extends ContextMenu {

    private MenuItem addFileMenu;

    private MenuItem removeFileMenu;

    private SPLCommandClient command;

    public SPLFileManagerContextMenu()
    {
        addFileMenu = new MenuItem(OpenedProjectModel.getInstance().getLanguage().getContextAddFileText());
        addFileMenu.setOnAction(new SPLCommandClient());
        addFileMenu.setId(Actions.ADD_FILE_DIALOG_ID);

        removeFileMenu = new MenuItem( OpenedProjectModel.getInstance().getLanguage().getContextDeleteFileText());
        removeFileMenu.setOnAction(new SPLCommandClient());
        removeFileMenu.setId(Actions.REMOVE_FILE_DIALOG_ID);

        getItems().addAll(addFileMenu, removeFileMenu);
    }
}
