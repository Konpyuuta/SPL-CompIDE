
package ui;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Maurice Amon
 */
public abstract class GuiFactory {   
        
    public abstract MenuBar createMenuBar();
    
    public abstract ToolBar createToolBar();
    
    public abstract TextArea createEditor();

    public abstract TabPane createTabPane();

    public abstract StackPane createStackPane();

    public abstract Label createLabel();
    
    public abstract HBox createExecutionComponent();

    public abstract TreeView createFileManager();
}
