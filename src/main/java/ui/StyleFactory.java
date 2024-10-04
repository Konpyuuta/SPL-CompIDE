package ui;

import javafx.scene.control.*;
import ui.components.*;

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
        return new SPLEditor();
    }

    @Override
    public Label createLabel() {
        return new SPLLabel();
    }

    @Override
    public TextArea createOutput() {
        return new SPLOutput();
    }

    @Override
    public TreeView createFileManager() {
        return SPLFileManager.getInstance();
    }


}
