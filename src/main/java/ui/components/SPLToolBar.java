package ui.components;

import commands.Actions;
import commands.SPLCommandClient;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import splprime.Observer;

/**
 *
 * @author Maurice Amon
 */

public class SPLToolBar extends ToolBar implements Observer {

    private SPLCommandClient splCommandClient = new SPLCommandClient();

    private final Button RUN_TOOL = new Button("", new ImageView("/run2.png"));

    private final Button SAVE_TOOL = new Button("", new ImageView("/save.png"));

    public SPLToolBar() {
        initComponents();
        setGraphics();
        setActions();
        super.getItems().addAll(RUN_TOOL, SAVE_TOOL);
        super.setStyle("-fx-background-color: linear-gradient(to bottom, #fcfff4 0%,#dfe5d7 40%,#c1beb2 100%);");
    }

    private void initComponents() {
        SAVE_TOOL.setCursor(Cursor.HAND);
        SAVE_TOOL.setTooltip(new Tooltip("Save the current file"));
        RUN_TOOL.setCursor(Cursor.HAND);
        RUN_TOOL.setTooltip(new Tooltip("Run program"));
        SAVE_TOOL.setDisable(true);
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
    }

    private void setActions() {
        RUN_TOOL.setId(Actions.RUN_ID);
        RUN_TOOL.setOnAction(splCommandClient);
        SAVE_TOOL.setId(Actions.SAVE_FILE_ID);
        SAVE_TOOL.setOnAction(splCommandClient);
    }

    @Override
    public void update(String text) {
        switch (text) {
            case "loaded":
                SAVE_TOOL.setDisable(false);
                break;
            case "closed":
                SAVE_TOOL.setDisable(true);
                break;
        }
    }
}
