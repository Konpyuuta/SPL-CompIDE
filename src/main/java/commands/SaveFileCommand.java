package commands;

import model.OpenedFileModel;
import model.OpenedProjectModel;
import ui.components.EditorTab;
import ui.components.MultiTabEditor;

import java.util.List;

/**
 *
 * @author Maurice Amon
 */
public class SaveFileCommand implements Command {

    @Override
    public void execute() {
        List<OpenedFileModel> openedFileList = OpenedProjectModel.getInstance().getOpenedFileList();
        for(OpenedFileModel file : openedFileList) {
            file.saveFile();
        }
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }


}
