package model.languages;

import java.util.Arrays;
import java.util.List;

public abstract class Language {

    private static Language language;

    public static Language configuredLanguage(String language) {
        Language lang;
        switch (language) {
            case "ENGLISH":
                lang = new English();
                break;
            case "German":
                lang = new German();
                break;
            case "Japanese":
                lang = new Japanese();
                break;
            default:
                lang = new English();
        }
        return lang;
    }

    public Language getLanguage() {
        return language;
    }


    public final static List<String> ALL_LANGUAGES = Arrays.asList("English", "German", "Japanese");

    // General Variables ..

    protected String languageName;

    protected String applicationName;

    // Start Window

    public String getLanguageName() {
        return languageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getStartWindowTitle() {
        return startWindowTitle;
    }

    public String getCreateNewProject() {
        return createNewProject;
    }

    public String getConfigureMainfileText() {
        return configureMainfileText;
    }

    public String getConfigureDesignText() {
        return configureDesignText;
    }

    public String getConfigureLanguageText() {
        return configureLanguageText;
    }

    public String getConfigureDescriptionText() {
        return configureDescriptionText;
    }

    public String getConfigureTitleText() {
        return configureTitleText;
    }

    public String getContextDeleteFileText() {
        return contextDeleteFileText;
    }

    public String getContextAddFileText() {
        return contextAddFileText;
    }

    public String getResumeButtonText() {
        return ResumeButtonText;
    }

    public String getOutputNameText() {
        return outputNameText;
    }

    public String getEditName() {
        return editName;
    }

    public String getRunName() {
        return runName;
    }

    public String getConfigName() {
        return configName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getInfoMenuItemText() {
        return infoMenuItemText;
    }

    public String getDebugMenuItemText() {
        return debugMenuItemText;
    }

    public String getRunMenuItemText() {
        return runMenuItemText;
    }

    public String getConfigurationMenuItemText() {
        return configurationMenuItemText;
    }

    public String getOpenProjectMenuItemText() {
        return openProjectMenuItemText;
    }

    public String getCreateProjectMenuItemText() {
        return createProjectMenuItemText;
    }

    public String getExitMenuItemText() {
        return exitMenuItemText;
    }

    public String getInfoMenuText() {
        return InfoMenuText;
    }

    public String getRunMenuText() {
        return RunMenuText;
    }

    public String getFileMenuText() {
        return FileMenuText;
    }

    public String getCancelButtonText() {
        return cancelButtonText;
    }

    public String getConfirmButtonText() {
        return confirmButtonText;
    }

    public String getCreateNewProjectMainFileLabel() {
        return createNewProjectMainFileLabel;
    }

    public String getCreateNewProjectNameLabel() {
        return createNewProjectNameLabel;
    }

    public String getCreateNewProjectLocationChooser() {
        return createNewProjectLocationChooser;
    }

    public String getCreateNewProjectLocationLabel() {
        return createNewProjectLocationLabel;
    }

    public String getCreateNewProjectDescription() {
        return createNewProjectDescription;
    }

    public String getCreateNewProjectTitle() {
        return createNewProjectTitle;
    }

    public String getOpenProject() {
        return openProject;
    }

    protected String startWindowTitle;

    protected String createNewProject;

    protected String openProject;

    // Create a new Project dialog

    protected String createNewProjectTitle;

    protected String createNewProjectDescription;

    protected String createNewProjectLocationLabel;

    protected String createNewProjectLocationChooser;

    protected String createNewProjectNameLabel;

    protected String createNewProjectMainFileLabel;

    // Global Confirm and Cancel button

    protected String confirmButtonText;

    protected String cancelButtonText;

    // Menubar ..

    protected String FileMenuText;

    protected String RunMenuText;

    protected String InfoMenuText;

    // Menu Items ..

    protected String exitMenuItemText;

    protected String createProjectMenuItemText;

    protected String openProjectMenuItemText;

    protected String configurationMenuItemText;

    protected String runMenuItemText;

    protected String debugMenuItemText;

    protected String infoMenuItemText;


    protected String fileName;

    protected String configName;

    protected String runName;

    protected String editName;

    // Editor ..

    protected String outputNameText;

    protected String ResumeButtonText;

    // Contextdialog ..

    protected String contextAddFileText;

    protected String contextDeleteFileText;

    // Configure Project Dialog ..

    protected String configureTitleText;

    protected String configureDescriptionText;
    protected String configureLanguageText;

    protected String configureDesignText;

    protected String configureMainfileText;

    // Create Error Dialog

    protected String errorMessageText;

    public String getErrorMessageText() {
        return errorMessageText;
    }

    public String getInfoMessageText() {
        return infoMessageText;
    }
// Info dialog

    protected String infoMessageText;

    public String getInfoHeaderText() {
        return infoHeaderText;
    }

    protected String infoHeaderText;

    // Add file dialog

    public String getAddFileHeaderText() {
        return addFileHeaderText;
    }

    public String getAddFileTitleText() {
        return addFileTitleText;
    }

    protected String addFileTitleText;
    protected String addFileHeaderText;



}
