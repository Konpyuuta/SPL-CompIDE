package ui.dialogs;

import commands.Actions;
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
import model.OpenedProjectModel;
import model.languages.Language;
import ui.View;

import java.io.File;
import java.util.ArrayList;

public class ConfigureProjectDialog extends View {

    private Language language = OpenedProjectModel.getInstance().getLanguage();

    public final Dialog<Pair<String, String>> CONFIG_PROJECT_DIALOG = new Dialog<>();

    private ChoiceBox languageChoiceBox = new ChoiceBox();

    private ChoiceBox designChoiceBox = new ChoiceBox();

    private ChoiceBox mainFileChoiceBox = new ChoiceBox();

    private final Label design = new Label(language.getConfigureDesignText());

    private final Label NAME_LABEL = new Label(language.getConfigureMainfileText());

    private final Label LANGUAGE_LABEL = new Label(language.getConfigureLanguageText());

    private final GridPane DIALOG_LAYOUT = new GridPane();

    private final Button CONFIRM_BUTTON = new Button(language.getConfirmButtonText());

    private final Button CANCEL_BUTTON = new Button(language.getCancelButtonText());

    public ConfigureProjectDialog(ArrayList<String> files) {
        ((Stage) CONFIG_PROJECT_DIALOG.getDialogPane().getScene().getWindow()).setOnCloseRequest((WindowEvent event1) -> {
            CONFIG_PROJECT_DIALOG.close();
            ((Stage) CONFIG_PROJECT_DIALOG.getDialogPane().getScene().getWindow()).close();
        });
        for(String file : files) {
            mainFileChoiceBox.getItems().add(file);
        }
        mainFileChoiceBox.getSelectionModel().select(0);
        initLanguageOptions();
        initDesignOptions();
    }

    private void initLanguageOptions() {
        for(String language : Language.ALL_LANGUAGES) {
            languageChoiceBox.getItems().add(language);
        }
    }

    private void initDesignOptions() {
        designChoiceBox.getItems().add("Standard");
    }

    public void initializeActions(EventHandler<ActionEvent> event) {
        CONFIRM_BUTTON.setId(Actions.CONFIGURE_ACCEPT_ID);
        CONFIRM_BUTTON.setOnAction(event);
        CANCEL_BUTTON.setId(Actions.CONFIGURE_CANCEL_ID);
        CANCEL_BUTTON.setOnAction(event);
    }

    @Override
    public void prepareView() {
        CONFIG_PROJECT_DIALOG.setTitle(language.getConfigureTitleText());
        CONFIG_PROJECT_DIALOG.setHeaderText(language.getConfigureDescriptionText());
        CONFIG_PROJECT_DIALOG.setGraphic(new ImageView("/dir-dialog.png"));
        DIALOG_LAYOUT.setVgap(10);
        DIALOG_LAYOUT.setHgap(10);
        DIALOG_LAYOUT.setPadding(new Insets(20, 10, 10, 10));
        DIALOG_LAYOUT.add(LANGUAGE_LABEL, 0, 0);
        DIALOG_LAYOUT.add(NAME_LABEL, 0, 1);
        DIALOG_LAYOUT.add(languageChoiceBox, 1, 0);
        DIALOG_LAYOUT.add(mainFileChoiceBox, 1, 1);
        DIALOG_LAYOUT.add(design, 0, 2);
        DIALOG_LAYOUT.add(designChoiceBox, 1, 2);
    }

    @Override
    public void initComponents() {
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(CONFIRM_BUTTON, CANCEL_BUTTON);
        languageChoiceBox.setPrefWidth(150);
        designChoiceBox.setPrefWidth(150);
        mainFileChoiceBox.setPrefWidth(150);
        design.setFont(Font.font(16));
        NAME_LABEL.setFont(Font.font(16));
        LANGUAGE_LABEL.setFont(Font.font(16));
        DIALOG_LAYOUT.add(buttonBox, 1, 5);

        CONFIG_PROJECT_DIALOG.getDialogPane().setContent(DIALOG_LAYOUT);
        Stage stage = (Stage) CONFIG_PROJECT_DIALOG.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/dir.png").toString()));
    }


    @Override
    public void showView() {
        CONFIG_PROJECT_DIALOG.show();
    }



    public String getMainFileName() {
        return (String)this.mainFileChoiceBox.getValue();
    }

    public String getLanguage() {
        return (String)this.languageChoiceBox.getValue();
    }

    public String getDesign() {
        return (String)this.designChoiceBox.getValue();
    }

}
