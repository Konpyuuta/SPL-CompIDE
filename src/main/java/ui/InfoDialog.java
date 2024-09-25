package ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InfoDialog extends View {

    private final Alert INFO_DIALOG = new Alert(Alert.AlertType.INFORMATION);

    private final Label AUTHOR_LABEL = new Label();

    private final Label SOFTWARE_INFO_LABEL = new Label("SPL Compiler was developed as part of the M. Sc. course\nin \"Compiler  Construction\" at the University of Bern.\n"
            + "Maurice Amon\nmaurice.amon@outlook.com");

    private final GridPane INFO_DIALOG_LAYOUT = new GridPane();

    @Override
    public void prepareView() {

    }

    public void initComponents() {
        AUTHOR_LABEL.setStyle("-fx-font-weight: bold;");
        AUTHOR_LABEL.setText("Info: \n\nAuthor: \nContact: \n");
        INFO_DIALOG_LAYOUT.add(AUTHOR_LABEL, 0, 1);
        INFO_DIALOG_LAYOUT.add(SOFTWARE_INFO_LABEL, 1, 1);
        INFO_DIALOG.setTitle("SPL Compiler: Information");
        INFO_DIALOG.setHeaderText("Information about this software.");
        INFO_DIALOG.getDialogPane().setContent(INFO_DIALOG_LAYOUT);
        Stage stage = (Stage) INFO_DIALOG.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/logo.png").toString()));
    }

    public void showView() {
        INFO_DIALOG.showAndWait();
    }
}
