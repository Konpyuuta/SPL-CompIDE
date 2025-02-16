package ui.components;

import commands.debugging.SPLDebuggingCommandClient;
import javafx.scene.control.TextArea;

public class SPLEditor extends TextArea {

    private final Integer HEIGHT = 450;

    public static SPLEditor splEditor;

    private SPLEditor() {
        setPrefHeight(HEIGHT);
    }

    public static SPLEditor getInstance() {
        if (splEditor == null) {
            splEditor = new SPLEditor();
        }
        return splEditor;
    }

    private void initializeActions() {
        //this.setOnMouseClicked(new SPLDebuggingCommandClient());
    }
}
