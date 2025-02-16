package commands.debugging;

import commands.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class SPLDebuggingCommandClient implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        Command command;
        switch (getItemId(actionEvent)) {
            case Actions.EDITOR_MOUSE_CLICK_ID:
                command = new DoNothingCommand();
                break;
            case Actions.DEBUG_ID:
                command = new DebugCommand();
                break;
            case Actions.RESUME_ID:
                command = new ResumeExecutionCommand();
                break;
            default:
                command = new DoNothingCommand();
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
