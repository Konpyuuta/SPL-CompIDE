package commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class SPLCommandClient implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
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
            default:
                command = new DoNothingCommand();
                break;
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
