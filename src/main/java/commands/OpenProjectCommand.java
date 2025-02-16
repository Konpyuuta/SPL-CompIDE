package commands;

import commands.config.ParseConfigFileCommand;
import commands.start.StartEditorCommand;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.OpenedProjectModel;
import model.languages.Language;
import ui.MainWindow;
import ui.components.SPLToolBar;
import ui.dialogs.ErrorMessageDialog;
import ui.components.SPLFileItem;
import ui.components.SPLFileManager;

import java.io.File;

public class OpenProjectCommand implements Command {

    private final String configFileName = "config.splc";

    @Override
    public void execute() {
        File choosenDir = chooseDirectory();
        if(isDirectorySPLProject(choosenDir)) {
            ParseConfigFileCommand parseConfigFileCommand = new ParseConfigFileCommand(choosenDir);
            parseConfigFileCommand.execute();
            SPLFileItem rootItem = new SPLFileItem(choosenDir);
            rootItem.setIsDirectory(true);
            traverseDirTree(rootItem);
            OpenedProjectModel.getInstance().setLanguage(Language.configuredLanguage(parseConfigFileCommand.getLanguageName()));
            StartEditorCommand startEditorCommand = new StartEditorCommand();
            startEditorCommand.execute();
            SPLFileManager.getInstance().initializeItems(rootItem);
            SPLFileManager.getInstance().setAbsolutePathOpenProject(choosenDir.getAbsolutePath());
            OpenedProjectModel.getInstance().addObserver((SPLToolBar)MainWindow.getInstance(null).toolBar);
            OpenedProjectModel.getInstance().setProjectPath(choosenDir.getAbsolutePath());
            OpenedProjectModel.getInstance().setMainFileName(parseConfigFileCommand.getMainFile());
            OpenedProjectModel.getInstance().setConfigPath(choosenDir.getAbsolutePath() + File.separator + configFileName);

        } else {
            // Show error dialog ..
            ErrorMessageDialog errorMessageDialog = new ErrorMessageDialog(choosenDir.getAbsolutePath());
            errorMessageDialog.initComponents();
            errorMessageDialog.prepareView();
            errorMessageDialog.showView();
        }

    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    private File chooseDirectory() {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(stage);

        return selectedDir;
    }

    private Boolean isDirectorySPLProject(File choosenDir) {
        for(File file : choosenDir.listFiles()) {
            if(file.getName().equals(configFileName)) {
                return true;
            }
        }
        return false;
    }

    private void traverseDirTree(SPLFileItem root) {
        if(root.getFile().listFiles() == null) {
            return;
        }
        for(File file : root.getFile().listFiles()) {
            SPLFileItem child = new SPLFileItem(file);
            if(file.isDirectory()) {
                // Create TreeItem with dir image ..
                child.setIsDirectory(true);
            } else {
                // Create TreeItem with file image ..
                child.setIsDirectory(false);
            }
            root.getChildren().add(child);
            traverseDirTree(child);
        }
    }
}
