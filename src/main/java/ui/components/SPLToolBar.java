package ui.components;

import commands.Actions;
import commands.SPLCommandClient;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class SPLToolBar extends ToolBar {

    private SPLCommandClient splCommandClient = new SPLCommandClient();

    private final Button RUN_TOOL = new Button("", new ImageView("/run2.png"));

    public SPLToolBar() {
        initComponents();
        setGraphics();
        setActions();
        super.getItems().addAll(RUN_TOOL);
        super.setStyle("-fx-background-color: linear-gradient(to bottom, #fcfff4 0%,#dfe5d7 40%,#c1beb2 100%);");
    }

    private void initComponents() {
        RUN_TOOL.setCursor(Cursor.HAND);
        RUN_TOOL.setTooltip(new Tooltip("Run program"));
    }

    private void setGraphics() {
        ImageView imgRun = new ImageView("/run2.png");
        imgRun.setFitHeight(36);
        imgRun.setFitWidth(31);
        RUN_TOOL.setGraphic(imgRun);

    }

    private void setActions() {
        RUN_TOOL.setId(Actions.RUN_ID);
        RUN_TOOL.setOnAction(splCommandClient);
    }
}
