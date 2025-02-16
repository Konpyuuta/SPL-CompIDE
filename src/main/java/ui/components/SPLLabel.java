package ui.components;

import javafx.scene.control.Label;
import model.OpenedProjectModel;

public class SPLLabel extends Label {

    private final String TEXT = OpenedProjectModel.getInstance().getLanguage().getOutputNameText();

    private final Integer WIDTH = 1050;

    private final String ID = "output";

    public SPLLabel() {
        setText(TEXT);
        setMinWidth(WIDTH);
        setId(ID);
    }
}
