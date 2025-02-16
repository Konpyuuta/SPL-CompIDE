package commands.editor;

import commands.Command;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class UpdateFileCommand implements Command, ChangeListener {

    public final Integer UID;

    public UpdateFileCommand(Integer UID) {
        this.UID = UID;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    @Override
    public void changed(ObservableValue observableValue, Object object, Object t1) {

    }
}
