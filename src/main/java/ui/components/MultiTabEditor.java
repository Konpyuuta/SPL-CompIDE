package ui.components;

import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import ui.components.editor.StandardEditor;

public class MultiTabEditor extends TabPane {

    private static MultiTabEditor multiTabEditorInstance;

    private MultiTabEditor() {
        Tab tab = new Tab();
        tab.closableProperty().setValue(false);
        tab.setText("Welcome to SPL' Compiler!");
        StandardEditor.getInstance().setCode(null, "// Welcome to SPL' Compiler! A small IDE developed for teaching purposes at the University of Bern.");
        tab.setContent(StandardEditor.getInstance());
        getTabs().add(tab);
    }

    public static MultiTabEditor getInstance() {
        if (multiTabEditorInstance == null) {
            multiTabEditorInstance = new MultiTabEditor();
        }
        return multiTabEditorInstance;
    }

    public void addTab(Tab tab) {
        getTabs().add(tab);
        getSelectionModel().select(tab);
    }

    public EditorTab getEditorTabByID(Integer id) {
        for (Tab tab : getTabs()) {
            if(tab instanceof EditorTab) {
                EditorTab editorTab = (EditorTab) tab;
                if (editorTab.getOpenFileID().equals(id)) {
                    return editorTab;
                }
            }
        }
        return null;
    }

    public void removeTab(Tab tab) {
        getTabs().remove(tab);
    }
}
