

package ui;

import javafx.scene.control.*;

/**
 *
 * @author Maurice Amon
 */
public abstract class View {
    
    public MenuBar menuBar;
    
    public ToolBar toolBar;
    
    public TextArea editor;

    public Label separator;

    public TextArea output;

    public abstract void prepareView();
    
    public abstract void initComponents();
    
    public abstract void showView();
    
}
