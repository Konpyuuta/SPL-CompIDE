package model.languages;

public class English extends Language {


    public English() {
        languageName = "English";
        applicationName = "SPL' CompIDE";
        startWindowTitle = "Welcome to SPL' CompIDE! ";
        createNewProject = "Create a new project";
        openProject = "Open a project";
        createNewProjectTitle = "SPL' CompIDE: Create a new project";
        createNewProjectDescription = "Create a new project.";
        createNewProjectLocationLabel = "Location:";
        createNewProjectLocationChooser = "Choose Directory:";
        createNewProjectNameLabel = "Name of the project:";
        createNewProjectMainFileLabel = "Name of the Main-file:";
        confirmButtonText = "Confirm";
        cancelButtonText = "Cancel";
        FileMenuText = "File";
        RunMenuText = "Run";
        InfoMenuText = "Info";
        exitMenuItemText = "Exit";
        openProjectMenuItemText = "Open Project";
        createProjectMenuItemText = "Create Project";
        configurationMenuItemText = "Configuration";
        runMenuItemText = "Run Program";
        debugMenuItemText = "Debug Program";
        infoMenuItemText = "Show Info";
        outputNameText = "Output";
        ResumeButtonText = "Resume";
        contextAddFileText = "Add File";
        contextDeleteFileText = "Delete File";
        configureTitleText = "SPL' CompIDE: Configure the Project";
        configureDescriptionText = "Project Configuration";
        configureLanguageText = "Language:";
        configureMainfileText = "Name of Main-File:";
        configureDesignText = "Design:";
        errorMessageText = "The selected directory is not a SPL-project, \"\n" +
                "            + \"as it does not contain a configuration file \\\"spl.config\\\".";
        infoMessageText = "SPL Compiler was developed as part of the M. Sc. course\nin \"Compiler  Construction\" at the University of Bern.\n" +
                "Maurice Amon\nmaurice.amon@outlook.com";
        infoHeaderText = "Information about this software.";
        addFileTitleText = "SPL' CompIDE: Add new file";
        addFileHeaderText = "Add a new file";
    }

}
