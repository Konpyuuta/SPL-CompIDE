package ui.dialogs;

import commands.Actions;
import commands.start.SPLStartCommandClient;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import ui.View;

public class StartWindowDialog extends View {

    public final Dialog<Pair<String, String>> START_WINDOW_LAYOUT = new Dialog<>();

    private final GridPane DIALOG_LAYOUT = new GridPane();

    private final Button NEW_PROJECT_BUTTON = new Button();

    private final Button OPEN_PROJECT_BUTTON = new Button();

    private final Label NEW_PROJECT_LABEL = new Label("Create a new project");

    private final Label OPEN_PROJECT_LABEL = new Label("Open a project");

    private final SPLStartCommandClient startCommandClient = new SPLStartCommandClient();

    public static StartWindowDialog startWindowDialog;

    private StartWindowDialog() {
        ((Stage) START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow()).setOnCloseRequest((WindowEvent event1) -> {
            START_WINDOW_LAYOUT.close();
            ((Stage) START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow()).close();
        });
    }

    public static StartWindowDialog getStartWindowDialogInstance() {
        if (startWindowDialog == null) {
            startWindowDialog = new StartWindowDialog();
        }
        return startWindowDialog;
    }

    public static void destroyInstance() {
        startWindowDialog = null;
    }

    @Override
    public void prepareView() {
        START_WINDOW_LAYOUT.setTitle("SPL' Compiler: Welcome");
        START_WINDOW_LAYOUT.setHeaderText("Welcome to SPL' Compiler!");
        createImageViews();
        Rectangle rect = new Rectangle(64, 64);
        ImagePattern pattern = new ImagePattern(new Image(StartWindowDialog.class.getResourceAsStream("/logo.png")));
        rect.setFill(pattern);
        START_WINDOW_LAYOUT.setGraphic(rect);
        DIALOG_LAYOUT.setVgap(10);
        DIALOG_LAYOUT.setHgap(50);
        DIALOG_LAYOUT.setPadding(new Insets(20, 0, 10, 20));
        DIALOG_LAYOUT.add(NEW_PROJECT_BUTTON, 0, 0);
        DIALOG_LAYOUT.add(OPEN_PROJECT_BUTTON, 1, 0);
        DIALOG_LAYOUT.add(NEW_PROJECT_LABEL, 0, 1);
        DIALOG_LAYOUT.add(OPEN_PROJECT_LABEL, 1, 1);
    }

    private void createImageViews() {
        Circle newProject = new Circle(32);
        ImagePattern pattern = new ImagePattern(new Image(StartWindowDialog.class.getResourceAsStream("/create.png")));
        newProject.setFill(pattern);
        NEW_PROJECT_BUTTON.setGraphic(newProject);
        NEW_PROJECT_BUTTON.setId(Actions.CREATE_PROJECT_ID);
        NEW_PROJECT_BUTTON.setShape(newProject);

        Circle openProject = new Circle(32);
        ImagePattern pattern2 = new ImagePattern(new Image(StartWindowDialog.class.getResourceAsStream("/dir-start.png")));
        openProject.setFill(pattern2);
        OPEN_PROJECT_BUTTON.setGraphic(openProject);
        OPEN_PROJECT_BUTTON.setId(Actions.OPEN_PROJECT_ID);
        OPEN_PROJECT_BUTTON.setShape(newProject);
    }

    @Override
    public void initComponents() {
        START_WINDOW_LAYOUT.getDialogPane().setContent(DIALOG_LAYOUT);
        START_WINDOW_LAYOUT.getDialogPane().getScene().getStylesheets().addAll(this.getClass().getResource("/start-window.css").toExternalForm());
        NEW_PROJECT_BUTTON.setOnAction(startCommandClient);
        OPEN_PROJECT_BUTTON.setOnAction(startCommandClient);
        Stage stage = (Stage) START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/logo.png").toString()));
    }

    @Override
    public void showView() {
        START_WINDOW_LAYOUT.show();
    }

    public void closeView() {
        ((Stage) START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow()).close();
        START_WINDOW_LAYOUT.close();
    }
}
