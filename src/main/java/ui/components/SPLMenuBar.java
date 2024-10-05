package ui.components;

import commands.Actions;
import commands.SPLCommandClient;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class SPLMenuBar extends MenuBar {

    private SPLCommandClient splCommandClient = new SPLCommandClient();

    private final Menu FILE_MENU = new Menu("File");

    private final Menu RUN_MENU = new Menu("Run");

    private final Menu INFO_MENU = new Menu("Info");

    private final MenuItem CREATE_PROJECT_ITEM = new MenuItem("Create Project", new ImageView("/create_mini.png"));

    private final MenuItem OPEN_PROJECT_ITEM = new MenuItem("Open Project", new ImageView("/dir.png"));

    private final MenuItem CLOSE_ITEM = new MenuItem("Exit", new ImageView("/exit.png"));

    private final MenuItem RUN_PROGRAM_ITEM = new MenuItem("Run program", new ImageView("/run.png"));

    private final MenuItem DEBUG_PROGRAM_ITEM = new MenuItem("Debug program", new ImageView("/debug.png"));

    private final MenuItem INFO_ITEM = new MenuItem("Show info", new ImageView("/info_little.png"));

    public SPLMenuBar() {
        FILE_MENU.setId("file");
        RUN_MENU.setId("config-run");
        RUN_MENU.getItems().addAll(RUN_PROGRAM_ITEM, DEBUG_PROGRAM_ITEM);
        INFO_MENU.setId("information");
        INFO_MENU.getItems().addAll(INFO_ITEM);
        CREATE_PROJECT_ITEM.setId(Actions.CREATE_PROJECT_ID);
        OPEN_PROJECT_ITEM.setId(Actions.OPEN_PROJECT_ID);
        FILE_MENU.getItems().addAll(OPEN_PROJECT_ITEM, CREATE_PROJECT_ITEM, CLOSE_ITEM);

        super.getMenus().addAll(FILE_MENU, RUN_MENU, INFO_MENU);
        super.setHover(true);
        initializeActions();
    }

    private void initializeActions() {
        RUN_PROGRAM_ITEM.setId(Actions.RUN_ID);
        DEBUG_PROGRAM_ITEM.setId(Actions.DEBUG_ID);
        DEBUG_PROGRAM_ITEM.setDisable(true);
        INFO_ITEM.setId(Actions.INFO_ID);
        CLOSE_ITEM.setId(Actions.EXIT_ID);
        super.getMenus().stream().forEach((menu) -> {
            menu.getItems().stream().forEach((item) -> {
                item.setOnAction(splCommandClient);
            });
        });
    }
}
