package model;

import model.languages.English;
import model.languages.Language;
import splprime.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maurice Amon
 */

public class OpenedProjectModel extends Observable {

    private String projectPath;

    private String configPath;

    private String mainFileName;

    private Language language;

    private List<OpenedFileModel> openedFileList = new ArrayList<>();

    public static OpenedProjectModel openedProjectModel;

    private OpenedProjectModel() {

    }

    public static OpenedProjectModel getInstance() {
        if (openedProjectModel == null) {
            openedProjectModel = new OpenedProjectModel();
        }
        return openedProjectModel;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public void setMainFileName(String mainFileName) {
        this.mainFileName = mainFileName;
    }
    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        if(language == null) {
            language = new English();
        }
        return language;
    }

    public String getMainFileName() {
        return mainFileName;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public void addOpenedFile(OpenedFileModel openedFile) {
        openedFileList.add(openedFile);
    }

    public void removeOpenedFile(OpenedFileModel openedFile) {
        openedFileList.remove(openedFile);
        if(openedFileList.size() == 0) {
            notifyObservers(Notifications.NOTHING_TO_BE_SAVED);
        }
    }

    public String getProjectPath() {
        return projectPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public List<OpenedFileModel> getOpenedFileList() {
        return openedFileList;
    }

    public OpenedFileModel getOpenedFileModelByID(int id) {
        for (OpenedFileModel openedFile : openedFileList) {
            if (openedFile.getId() == id) {
                return openedFile;
            }
        }
        return null;
    }

    public OpenedFileModel getOpenedFileModelByAbsolutePath(String path) {
        for (OpenedFileModel openedFile : openedFileList) {
            if(openedFile.getPath().equals(path)) {
                return openedFile;
            }
        }
        return null;
    }
}
