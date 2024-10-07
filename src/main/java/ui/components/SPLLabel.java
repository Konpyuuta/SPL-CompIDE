package ui.components;

import javafx.scene.control.Label;

public class SPLLabel extends Label {

    private final String TEXT = "Output";

    private final Integer WIDTH = 1130;

    private final String ID = "output";

    public SPLLabel() {
        setText(TEXT);
        setMinWidth(WIDTH);
        setId(ID);
    }
}
