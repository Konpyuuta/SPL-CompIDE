package commands;

import ui.components.SPLFileItem;
import ui.components.SPLFileManager;

public class RemoveFileCommand implements Command {

    public RemoveFileCommand() {

    }

    @Override
    public void execute() {
        SPLFileItem item = (SPLFileItem) SPLFileManager.getInstance().getSelectionModel().getSelectedItem();
        if(item != null) {
            item.getFile().delete();
            SPLFileItem rootItem = new SPLFileItem(SPLFileManager.getInstance().getAbsolutePathOpenProject());
            rootItem.setIsDirectory(true);
            SPLFileManager.getInstance().traverseDirTree(rootItem);
            SPLFileManager.getInstance().initializeItems(rootItem);
            rootItem.setExpanded(true);
        } else {

        }

    }

    @Override
    public void undo() {
        // No implementation yet ..
    }
}
