

package ui;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/** Abstract class that all View-designs of the main window have to extend.
 *
 * @author Maurice Amon
 */
public abstract class View {
    
    public MenuBar menuBar;
    
    public ToolBar toolBar;
    
    public TextArea editor;

    public StackPane pane;

    public TabPane tabPane;

    public Label separator;

    public HBox output;

    public TreeView fileManager;

    public abstract void prepareView();
    
    public abstract void initComponents();
    
    public abstract void showView();
    
}
