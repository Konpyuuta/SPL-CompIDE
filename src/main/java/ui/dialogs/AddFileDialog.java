package ui.dialogs;

import commands.AddFileCommand;
import commands.SPLCommandClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import model.OpenedProjectModel;
import model.languages.Language;
import ui.View;

public class AddFileDialog extends View {

    private Language language = OpenedProjectModel.getInstance().getLanguage();

    public final Dialog<Pair<String, String>> START_WINDOW_LAYOUT = new Dialog<>();

    private final GridPane DIALOG_LAYOUT = new GridPane();

    private final TextField FILE_NAME_FIELD = new TextField();

    private final Button OK_BUTTON = new Button(language.getConfirmButtonText());

    private final Button CANCEL_BUTTON = new Button(language.getCancelButtonText());

    private final Label FILE_NAME_LABEL = new Label("Filename: ");

    private SPLCommandClient splCommandClient = new SPLCommandClient();

    public AddFileDialog() {
        ((Stage) START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow()).setOnCloseRequest((WindowEvent event1) -> {
            START_WINDOW_LAYOUT.close();
            ((Stage) START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow()).close();
        });
    }

    @Override
    public void prepareView() {
        START_WINDOW_LAYOUT.setTitle(language.getAddFileTitleText());
        START_WINDOW_LAYOUT.setHeaderText(language.getAddFileHeaderText());
        Circle circle = new Circle(32);
        ImagePattern pattern = new ImagePattern(new Image(StartWindowDialog.class.getResourceAsStream("/create.png")));
        circle.setFill(pattern);
        START_WINDOW_LAYOUT.setGraphic(circle);
        DIALOG_LAYOUT.setVgap(10);
        DIALOG_LAYOUT.setHgap(20);
        DIALOG_LAYOUT.setPadding(new Insets(20, 10, 10, 20));
        DIALOG_LAYOUT.add(FILE_NAME_LABEL, 0, 0);
        DIALOG_LAYOUT.add(FILE_NAME_FIELD, 1, 0);
    }

    @Override
    public void initComponents() {
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(OK_BUTTON, CANCEL_BUTTON);
        DIALOG_LAYOUT.add(buttonBox, 0, 1);
        START_WINDOW_LAYOUT.getDialogPane().setContent(DIALOG_LAYOUT);
        START_WINDOW_LAYOUT.getDialogPane().getScene().getStylesheets().addAll(this.getClass().getResource("/start-window.css").toExternalForm());
        OK_BUTTON.setOnAction(e -> {
            new AddFileCommand(FILE_NAME_FIELD.getText()).execute();
            ((Stage)START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow()).close();
        });
        CANCEL_BUTTON.setOnAction(t -> ((Stage)START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow()).close());
        Stage stage = (Stage) START_WINDOW_LAYOUT.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/logo.png").toString()));
    }

    @Override
    public void showView() {
        START_WINDOW_LAYOUT.show();
    }
}
