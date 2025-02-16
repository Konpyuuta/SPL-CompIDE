package commands.config;

import commands.Actions;
import commands.Command;
import commands.DoNothingCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;
import model.OpenedProjectModel;
import ui.dialogs.ConfigureProjectDialog;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Maurice Amon
 */
public class ShowConfigDialogCommand implements Command, EventHandler<ActionEvent> {

    ConfigureProjectDialog configureProjectDialog;

    @Override
    public void execute() {
        configureProjectDialog = new ConfigureProjectDialog(fetchMainFileCandidates());
        configureProjectDialog.initComponents();
        configureProjectDialog.prepareView();
        configureProjectDialog.showView();
        configureProjectDialog.initializeActions(this);
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    private ArrayList<String> fetchMainFileCandidates() {
        ArrayList<String> candidates = new ArrayList<>();
        File[] files = new File(OpenedProjectModel.getInstance().getProjectPath()).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName();
                int i = name.lastIndexOf('.');
                if(name.substring(i+1).equals("spl")) {
                    candidates.add(name);
                }
            }
        }
        return candidates;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Command command;
        switch(getItemId(actionEvent)) {
            case Actions.CONFIGURE_ACCEPT_ID:
                command = new SaveConfigurationCommand(configureProjectDialog);
                break;
            case Actions.CONFIGURE_CANCEL_ID:
                configureProjectDialog.CONFIG_PROJECT_DIALOG.getDialogPane().getScene().getWindow().hide();
                configureProjectDialog = null;
                command = new DoNothingCommand();
                break;
            default:
                command = new DoNothingCommand();
        }
        command.execute();
    }

    private String getItemId(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            return ((Button) event.getSource()).getId();
        }
        if (event.getSource() instanceof MenuItem) {
            return ((MenuItem) event.getSource()).getId();
        }
        return null;
    }
}
