package ui.components.execution;

import commands.Actions;
import javafx.scene.Cursor;
import javafx.scene.control.Button;

/**
 *
 * @author Maurice Amon
 */

public class ResumeExecutionButton extends Button {

    private final String BUTTON_NAME = "Resume";

    private final Integer BUTTON_WIDTH = 250;

    public ResumeExecutionButton() {
        super();
        this.setText(BUTTON_NAME);
        this.setPrefWidth(BUTTON_WIDTH);
        this.setId(Actions.RESUME_ID);
        this.setCursor(Cursor.HAND);
        this.setDisable(true);
    }
}
