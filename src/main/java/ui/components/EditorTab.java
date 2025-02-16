package ui.components;

import javafx.scene.control.Tab;
import model.OpenedFileModel;
import model.OpenedProjectModel;
import splprime.Observer;
import ui.components.editor.Editor;
import ui.components.editor.MainEditor;
import ui.components.editor.StandardEditor;

/**
 *
 * @author Maurice Amon
 */

public class EditorTab extends Tab implements Observer {

    private Integer openFileID;

    private Boolean isMainTab;


    public EditorTab(Integer openFileID, Boolean isMainTab) {
        super();
        this.openFileID = openFileID;
        this.isMainTab = isMainTab;
        initializeAction();
    }

    public void initializeContent() {
        OpenedFileModel openedFile = OpenedProjectModel.getInstance().getOpenedFileModelByID(openFileID);
        setText(openedFile.getFileName());
        Editor editor;
        if(isMainTab) {
            editor = MainEditor.getInstance();
        } else {
            editor = new StandardEditor();
        }
        editor.setCode(openFileID, openedFile.getContent());
        setContent(editor);
    }

    private void initializeAction() {
        setOnCloseRequest(event -> {
            //
            OpenedFileModel model = OpenedProjectModel.getInstance().getOpenedFileModelByID(openFileID);
            OpenedProjectModel.getInstance().removeOpenedFile(model);
            MultiTabEditor.getInstance().removeTab(this);
        });
    }

    public Integer getOpenFileID() {
        return openFileID;
    }

    @Override
    public void update(Object object) {
       // openedFileModel = (OpenedFileModel) object;
    }
}
