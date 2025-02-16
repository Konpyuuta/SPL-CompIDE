package commands.start;

import commands.Command;
import ui.MainWindow;
import ui.StyleFactory;
import ui.dialogs.StartWindowDialog;

public class StartEditorCommand implements Command {

    @Override
    public void execute() {
        StartWindowDialog.getStartWindowDialogInstance().closeView();
        StartWindowDialog.destroyInstance();
        MainWindow mainWindow = MainWindow.getInstance(new StyleFactory());
        mainWindow.prepareView();
        mainWindow.initComponents();
        mainWindow.showView();
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }
}
