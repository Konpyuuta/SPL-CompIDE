package commands;

import commands.config.ShowConfigDialogCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/** Client class for all ActionEvents that have been initiated by clickable GUI-components ..
 *
 * @author Maurice Amon
 */

public class SPLCommandClient implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        SPLExecutorService splExecutorService;
        Command command;
        switch (getItemId(actionEvent)) {
            case Actions.RUN_ID:
                command = new RunCommand();
                break;
            case Actions.DEBUG_ID:
                command = new DebugCommand();
                break;
            case Actions.INFO_ID:
                command = new ShowInfoCommand();
                break;
            case Actions.EXIT_ID:
                command = new ExitCommand();
                break;
            case Actions.OPEN_PROJECT_ID:
                command = new OpenProjectCommand();
                break;
            case Actions.CREATE_PROJECT_ID:
                command = new CreateProjectCommand();
                break;
            case Actions.CONFIG_PROJECT:
                command = new ShowConfigDialogCommand();
                break;
            case Actions.SAVE_FILE_ID:
                command = new SaveFileCommand();
                break;
            case Actions.ADD_FILE_DIALOG_ID:
                command = new AddFileDialogCommand();
                break;
            case Actions.REMOVE_FILE_DIALOG_ID:
                command = new RemoveFileCommand();
                break;
            default:
                command = new DoNothingCommand();
                break;
        }
        splExecutorService = new SPLExecutorService(command);
        splExecutorService.runExecution();
    }

    /** Extract the ID from the GUI-component that fired the event ..
     *
     * @param event
     * @return
     */

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
