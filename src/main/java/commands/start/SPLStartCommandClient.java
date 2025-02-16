package commands.start;

import commands.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Maurice Amon
 */

public class SPLStartCommandClient implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        CommandComposite command = new CommandComposite();
        switch (getItemId(actionEvent)) {
            case Actions.CREATE_PROJECT_ID:
                command.addCommand(new CreateProjectCommand());
                break;
            case Actions.OPEN_PROJECT_ID:
                command.addCommand(new OpenProjectCommand());
                break;
            default:
                command.addCommand(new DoNothingCommand());
                break;
        }
        command.execute();
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
