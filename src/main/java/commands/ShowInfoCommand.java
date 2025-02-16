package commands;

import ui.dialogs.InfoDialog;

/** Command initiates the display of the info-window for the application ..
 *
 * @author Maurice Amon
 */

public class ShowInfoCommand implements Command {
    @Override
    public void execute() {
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.prepareView();
        infoDialog.initComponents();
        infoDialog.showView();
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }
}
