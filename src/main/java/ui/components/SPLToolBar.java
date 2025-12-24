package ui.components;

import commands.Actions;
import commands.SPLCommandClient;
import commands.debugging.SPLDebuggingCommandClient;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import model.Notifications;
import splprime.Observer;

/**
 *
 * @author Maurice Amon
 */

public class SPLToolBar extends ToolBar implements Observer {

    private SPLCommandClient splCommandClient = new SPLCommandClient();

    private SPLDebuggingCommandClient splDebuggingCommandClient = new SPLDebuggingCommandClient();

    private final Button RUN_TOOL = new Button("", new ImageView("/run2.png"));

    private final Button SAVE_TOOL = new Button("", new ImageView("/save.png"));

    private final Button DEBUG_TOOL = new Button("", new ImageView("/debug.png"));

    public SPLToolBar() {
        initComponents();
        setGraphics();
        setActions();
        super.getItems().addAll(RUN_TOOL, DEBUG_TOOL, SAVE_TOOL);
        super.setStyle("-fx-background-color: linear-gradient(to bottom, #fcfff4 0%,#dfe5d7 40%,#c1beb2 100%);");
    }

    private void initComponents() {
        DEBUG_TOOL.setCursor(Cursor.HAND);
        DEBUG_TOOL.setTooltip(new Tooltip("Debug your program"));
        SAVE_TOOL.setCursor(Cursor.HAND);
        SAVE_TOOL.setTooltip(new Tooltip("Save the current file"));
        RUN_TOOL.setCursor(Cursor.HAND);
        RUN_TOOL.setTooltip(new Tooltip("Run program"));
        SAVE_TOOL.setDisable(true);
        DEBUG_TOOL.setDisable(false);
        DEBUG_TOOL.setTooltip(new Tooltip("Debug Program"));
    }

    private void setGraphics() {
        ImageView imgRun = new ImageView("/run2.png");
        imgRun.setFitHeight(36);
        imgRun.setFitWidth(31);
        RUN_TOOL.setGraphic(imgRun);
        ImageView imgSave = new ImageView("/save.png");
        imgSave.setFitHeight(36);
        imgSave.setFitWidth(31);
        SAVE_TOOL.setGraphic(imgSave);
        ImageView imgDebug = new ImageView("/debug_big_icon.png");
        imgDebug.setFitHeight(36);
        imgDebug.setFitWidth(34);
        DEBUG_TOOL.setGraphic(imgDebug);
    }

    private void setActions() {
        RUN_TOOL.setId(Actions.RUN_ID);
        RUN_TOOL.setOnAction(splCommandClient);
        SAVE_TOOL.setId(Actions.SAVE_FILE_ID);
        SAVE_TOOL.setOnAction(splCommandClient);
        DEBUG_TOOL.setId(Actions.DEBUG_ID);
        DEBUG_TOOL.setOnAction(splDebuggingCommandClient);
    }

    @Override
    public void update(Object object) {
        String text = (String) object;
        switch (text) {
            case Notifications.LOADED:
                SAVE_TOOL.setDisable(false);
                break;
            case Notifications.CLOSED:
                SAVE_TOOL.setDisable(true);
                break;
            case Notifications.NOTHING_TO_BE_SAVED:
                SAVE_TOOL.setDisable(true);
                break;
        }
    }

    public Button getDebugButton() {
        return DEBUG_TOOL;
    }

    public Button getRunButton() {
        return RUN_TOOL;
    }
}
