package commands;

import ui.InfoDialog;

public class ShowInfoCommand implements Command {
    @Override
    public void execute() {
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.prepareView();
        infoDialog.initComponents();
        infoDialog.showView();
    }
}
