

package ui;

import javafx.scene.control.*;

/** Abstract class that all View-designs of the main window have to extend.
 *
 * @author Maurice Amon
 */
public abstract class View {
    
    public MenuBar menuBar;
    
    public ToolBar toolBar;
    
    public TextArea editor;

    public Label separator;

    public TextArea output;

    public TreeView fileManager;

    public abstract void prepareView();
    
    public abstract void initComponents();
    
    public abstract void showView();
    
}
