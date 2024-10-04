package ui.components;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Maurice Amon
 */

public class SPLFileManager extends TreeView {

    public static SPLFileManager splFileManager;

    private SPLFileManager() {
        setMinWidth(150);
    }

    public static SPLFileManager getInstance() {
        if (splFileManager == null) {
            splFileManager = new SPLFileManager();
        }
        return splFileManager;
    }

    public void initializeItems(SPLFileItem rootItem) {
        this.setRoot(rootItem);
    }

}
