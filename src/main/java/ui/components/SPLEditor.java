package ui.components;

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
}
