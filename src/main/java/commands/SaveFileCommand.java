package commands;

import model.OpenedFileModel;

/**
 *
 * @author Maurice Amon
 */
public class SaveFileCommand implements Command {

    @Override
    public void execute() {
        OpenedFileModel.createNewInstance().saveFile();
    }
}
