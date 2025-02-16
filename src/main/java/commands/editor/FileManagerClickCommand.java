package commands.editor;

import commands.Command;
import commands.OpenFileCommand;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ui.components.SPLFileItem;
import ui.components.SPLFileManager;

public class FileManagerClickCommand implements EventHandler<MouseEvent> {

    private int clickCounter = 0;

    private SPLFileItem selectedItem;

    private SPLFileManager splFileManager;

    public FileManagerClickCommand(SPLFileManager splFileManager) {
        this.splFileManager = splFileManager;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        switch(mouseEvent.getButton()) {
            case MouseButton.PRIMARY -> selectFileItem();
            case MouseButton.SECONDARY -> openDialog();
        }

    }

    private void selectFileItem() {
        selectedItem = (SPLFileItem) splFileManager.getSelectionModel().getSelectedItem();
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
    }

    private void openDialog() {

    }
}
