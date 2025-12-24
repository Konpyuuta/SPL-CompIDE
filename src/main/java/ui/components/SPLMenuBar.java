package ui.components;

import commands.Actions;
import commands.SPLCommandClient;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import model.OpenedProjectModel;

public class SPLMenuBar extends MenuBar {

    private SPLCommandClient splCommandClient = new SPLCommandClient();

    private final Menu FILE_MENU = new Menu(OpenedProjectModel.getInstance().getLanguage().getFileMenuText());

    private final Menu RUN_MENU = new Menu(OpenedProjectModel.getInstance().getLanguage().getRunMenuText());

    private final Menu INFO_MENU = new Menu(OpenedProjectModel.getInstance().getLanguage().getInfoMenuText());

    private final MenuItem CREATE_PROJECT_ITEM = new MenuItem(OpenedProjectModel.getInstance().getLanguage().getCreateProjectMenuItemText(), new ImageView("/create_mini.png"));

    private final MenuItem OPEN_PROJECT_ITEM = new MenuItem(OpenedProjectModel.getInstance().getLanguage().getOpenProjectMenuItemText(), new ImageView("/dir.png"));

    private final MenuItem CONFIG_ITEM = new MenuItem(OpenedProjectModel.getInstance().getLanguage().getConfigurationMenuItemText());

    private final MenuItem CLOSE_ITEM = new MenuItem(OpenedProjectModel.getInstance().getLanguage().getExitMenuItemText(), new ImageView("/exit.png"));

    private final MenuItem RUN_PROGRAM_ITEM = new MenuItem(OpenedProjectModel.getInstance().getLanguage().getRunMenuItemText(), new ImageView("/run.png"));

    private final MenuItem DEBUG_PROGRAM_ITEM;

    {
        ImageView debugIcon = new ImageView(
                getClass().getResource("/debug.png").toExternalForm()
        );
        debugIcon.setFitWidth(24);
        debugIcon.setFitHeight(24);
        debugIcon.setPreserveRatio(true);

        DEBUG_PROGRAM_ITEM = new MenuItem(
                OpenedProjectModel.getInstance()
                        .getLanguage()
                        .getDebugMenuItemText(),
                debugIcon
        );
    }
    private final MenuItem INFO_ITEM = new MenuItem(OpenedProjectModel.getInstance().getLanguage().getInfoMenuItemText(), new ImageView("/info_little.png"));

    public SPLMenuBar() {
        FILE_MENU.setId("file");
        RUN_MENU.setId("config-run");
        RUN_MENU.getItems().addAll(RUN_PROGRAM_ITEM, DEBUG_PROGRAM_ITEM);
        INFO_MENU.setId("information");
        INFO_MENU.getItems().addAll(INFO_ITEM);
        CREATE_PROJECT_ITEM.setId(Actions.CREATE_PROJECT_ID);
        CREATE_PROJECT_ITEM.setDisable(true);
        OPEN_PROJECT_ITEM.setId(Actions.OPEN_PROJECT_ID);
        CONFIG_ITEM.setId(Actions.CONFIG_PROJECT);
        FILE_MENU.getItems().addAll(OPEN_PROJECT_ITEM, CREATE_PROJECT_ITEM, CONFIG_ITEM, CLOSE_ITEM);

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

    public Menu getRunMenu() {
        return RUN_MENU;
    }
}
