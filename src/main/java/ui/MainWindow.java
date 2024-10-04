
package ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import splprime.Observer;

/** Class initializes the IDE Window of the application ..
 *
 * @author Maurice Amon
 */
public class MainWindow extends View implements Observer {

    private Scene scene;

    public Stage stage;

    private GuiFactory factory;

    private final VBox VERT_LAYOUT = new VBox();

    private final VBox SECOND_VERT_LAYOUT = new VBox();

    private final HBox HORIZONTAL_LAYOUT = new HBox();

    private static MainWindow mainWindow = null;

    private MainWindow(GuiFactory factory) {
        this.factory = factory;
    }

    public static MainWindow getInstance(GuiFactory factory) {
        if (mainWindow == null) {
            mainWindow = new MainWindow(factory);
        }
        return mainWindow;
    }

    @Override
    public void prepareView() {
        this.menuBar = factory.createMenuBar();
        this.toolBar = factory.createToolBar();
        this.editor = factory.createEditor();
        this.separator = factory.createLabel();
        this.output = factory.createOutput();
        this.fileManager = factory.createFileManager();
    }

    @Override
    public void initComponents() {
        Label label = new Label("Editor");
        HORIZONTAL_LAYOUT.getChildren().addAll(fileManager, SECOND_VERT_LAYOUT);
        VERT_LAYOUT.getChildren().addAll(this.menuBar, this.toolBar, HORIZONTAL_LAYOUT);
        SECOND_VERT_LAYOUT.getChildren().addAll(label, editor, separator, output);
        label.setMinWidth(960);
        label.setId("editor");
        stage = new Stage();
        scene = new Scene(VERT_LAYOUT, 960, 720);
        scene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("SPL Compiler");
        stage.setScene(scene);
        stage.setWidth(960);
        stage.setHeight(720);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/logo.png"));
    }

    @Override
    public void showView() {
        stage.show();
    }

    @Override
    public void update(String text) {
        output.setText(text);
    }
}
