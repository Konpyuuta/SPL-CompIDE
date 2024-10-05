package ui.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import ui.View;

import java.io.File;

public class CreateProjectDialog extends View {


    public final Dialog<Pair<String, String>> CREATE_PROJECT_LAYOUT = new Dialog<>();

    private TextField mainFileName = new TextField();

    private TextField directoryField = new TextField();

    private TextField projectNameField = new TextField();

    private DirectoryChooser directoryChooser = new DirectoryChooser();

    private Button directoryChooserButton = new Button("Choose directory");

    private final Label DIR_LABEL = new Label("Location: ");

    private final Label PROJECT_NAME_LABEL = new Label("Name of project: ");

    private final Label NAME_LABEL = new Label("Name of main-file: ");

    private final GridPane DIALOG_LAYOUT = new GridPane();

    private final Button CONFIRM_BUTTON = new Button("Bestätigen");

    private final Button CANCEL_BUTTON = new Button("Abbrechen");

    public CreateProjectDialog() {
        ((Stage) CREATE_PROJECT_LAYOUT.getDialogPane().getScene().getWindow()).setOnCloseRequest((WindowEvent event1) -> {
            CREATE_PROJECT_LAYOUT.close();
            ((Stage) CREATE_PROJECT_LAYOUT.getDialogPane().getScene().getWindow()).close();
        });
        directoryField.setDisable(true);
        initDirectoryChooserAction();
    }

    public void initializeActions(EventHandler<ActionEvent> event) {
        CONFIRM_BUTTON.setId("create");
        CONFIRM_BUTTON.setOnAction(event);
        CANCEL_BUTTON.setId("close");
        CANCEL_BUTTON.setOnAction(event);
    }

    @Override
    public void prepareView() {
        CREATE_PROJECT_LAYOUT.setTitle("SPL' Compiler: Create a new project");
        CREATE_PROJECT_LAYOUT.setHeaderText("Create a new project.");
        CREATE_PROJECT_LAYOUT.setGraphic(new ImageView("/dir-dialog.png"));
        DIALOG_LAYOUT.setVgap(10);
        DIALOG_LAYOUT.setHgap(10);
        DIALOG_LAYOUT.setPadding(new Insets(20, 10, 10, 10));
        DIALOG_LAYOUT.add(DIR_LABEL, 0, 0);
        DIALOG_LAYOUT.add(directoryField, 1, 0);
        DIALOG_LAYOUT.add(directoryChooserButton, 2, 0);
        DIALOG_LAYOUT.add(PROJECT_NAME_LABEL, 0, 1);
        DIALOG_LAYOUT.add(projectNameField, 1, 1);
        DIALOG_LAYOUT.add(NAME_LABEL, 0, 2);
        DIALOG_LAYOUT.add(mainFileName, 1, 2);
    }

    @Override
    public void initComponents() {
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(CONFIRM_BUTTON, CANCEL_BUTTON);
        directoryField.setPrefWidth(150);
        projectNameField.setPrefWidth(150);
        mainFileName.setPrefWidth(150);
        DIR_LABEL.setFont(Font.font(16));
        PROJECT_NAME_LABEL.setFont(Font.font(16));
        NAME_LABEL.setFont(Font.font(16));
        DIALOG_LAYOUT.add(buttonBox, 1, 5);

        CREATE_PROJECT_LAYOUT.getDialogPane().setContent(DIALOG_LAYOUT);
        Stage stage = (Stage) CREATE_PROJECT_LAYOUT.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/dir.png").toString()));
    }

    private void initDirectoryChooserAction() {
        EventHandler<ActionEvent> event =
                e -> {
                    File file = directoryChooser.showDialog((Stage) CREATE_PROJECT_LAYOUT.getDialogPane().getScene().getWindow());
                    if (file != null) {
                        directoryField.setText(file.getAbsolutePath());
                    }
                };
        directoryChooserButton.setOnAction(event);
    }

    @Override
    public void showView() {
        CREATE_PROJECT_LAYOUT.show();
    }

    public TextField getDirectoryField() {
        return this.directoryField;
    }

    public TextField getProjectNameField() {
        return this.projectNameField;
    }

    public TextField getMainFileName() {
        return this.mainFileName;
    }

}
