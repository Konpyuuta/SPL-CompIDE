package ui.components;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 *
 * @author Maurice Amon
 */

public class SPLFileItem extends TreeItem {

    private final File file;

    private Boolean isDirectory;

    public SPLFileItem(File file) {
        super(file.getName());
        this.file = file;
    }

    public SPLFileItem(String path) {
        super(path);
        this.file = new File(path);
    }

    public File getFile() {
        return file;
    }

    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
        setImage();
    }

    private void setImage() {
        if(isDirectory) {
            this.setGraphic(new ImageView("/dir.png"));
        } else {
            this.setGraphic(new ImageView("/file.png"));
        }
    }
}
