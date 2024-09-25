package ui.components;

import javafx.scene.control.TextArea;

public class SPLOutput extends TextArea {

    public SPLOutput() {
        super.setStyle("-fx-control-inner-background: #000000; -fx-text-fill: green;");
        super.setEditable(false);
    }
}
