
package ui;

import javafx.scene.control.*;

/**
 *
 * @author Maurice Amon
 */
public abstract class GuiFactory {   
        
    public abstract MenuBar createMenuBar();
    
    public abstract ToolBar createToolBar();
    
    public abstract TextArea createEditor();

    public abstract Label createLabel();
    
    public abstract TextArea createOutput();
}
