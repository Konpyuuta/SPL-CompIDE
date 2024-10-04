
package ui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ui.View;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Maurice Amon
 */
public class ErrorMessageDialog extends View {

    private final Alert ERROR_DIALOG = new Alert(Alert.AlertType.ERROR);

    private String path;

    private final String ERROR_MESSAGE = "The selected directory is not a SPL-project, "
            + "as it does not contain a configuration file \"spl.config\". ";

    public ErrorMessageDialog(String path) {
        this.path = path;
    }

    @Override
    public void prepareView() {

    }

    @Override
    public void initComponents() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        ERROR_DIALOG.setTitle("SPL' Compiler: Error");
        ERROR_DIALOG.setHeaderText("The directory " + path + " is not a SPL-project");
        ERROR_DIALOG.setContentText(ERROR_MESSAGE);
        Stage stage = (Stage) ERROR_DIALOG.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/error.png").toString()));
    }

    @Override
    public void showView() {
        ERROR_DIALOG.showAndWait();
    }
}
