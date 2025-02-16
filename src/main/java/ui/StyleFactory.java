package ui;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import ui.components.*;
import ui.components.editor.StandardEditor;
import ui.components.execution.SPLExecutionComponent;
import ui.components.execution.SPLOutput;

/** Concrete Factory for the standard design of the application ..
 *
 * @author Maurice
 */
public class StyleFactory extends GuiFactory {

    @Override
    public MenuBar createMenuBar() {
        return new SPLMenuBar();
    }

    @Override
    public ToolBar createToolBar() {
        return new SPLToolBar();
    }

    @Override
    public TextArea createEditor() {
        return SPLEditor.getInstance();
    }

    @Override
    public TabPane createTabPane() {
        return MultiTabEditor.getInstance();
    }

    @Override
    public StackPane createStackPane() {
        return StandardEditor.getInstance();
    }

    @Override
    public Label createLabel() {
        return new SPLLabel();
    }

    @Override
    public HBox createExecutionComponent() {
        return new SPLExecutionComponent();
    }

    @Override
    public TreeView createFileManager() {
        return SPLFileManager.getInstance();
    }


}
