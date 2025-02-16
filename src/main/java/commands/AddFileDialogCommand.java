package commands;

import ui.dialogs.AddFileDialog;

public class AddFileDialogCommand implements Command {

    public AddFileDialogCommand() {

    }

    @Override
    public void execute() {
        AddFileDialog addFileDialog = new AddFileDialog();
        addFileDialog.prepareView();
        addFileDialog.initComponents();
        addFileDialog.showView();
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }
}
