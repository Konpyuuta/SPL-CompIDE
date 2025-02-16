package commands.config;

import commands.Command;
import model.OpenedProjectModel;
import ui.dialogs.ConfigureProjectDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Maurice Amon
 */

public class SaveConfigurationCommand implements Command {

    private String language;

    private String mainFile;

    private String design;

    public SaveConfigurationCommand(ConfigureProjectDialog dialog) {
        this.language = dialog.getLanguage();
        this.mainFile = dialog.getMainFileName();
        this.design = dialog.getDesign();
        dialog.CONFIG_PROJECT_DIALOG.close();
        dialog.CONFIG_PROJECT_DIALOG.getDialogPane().getScene().getWindow().hide();
    }

    @Override
    public void execute() {
        String dirPath = OpenedProjectModel.getInstance().getConfigPath();
        String content = generateConfigFileContent();
        try{
            File file = new File(dirPath);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        }catch(IOException e){
            e.printStackTrace();
        }
        System.exit(1);
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    private String generateConfigFileContent() {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("LANGUAGE=").append(language).append("\n");
        contentBuilder.append("MAIN_FILE=").append(mainFile).append("\n");
        contentBuilder.append("DESIGN=").append(design).append("\n");
        return contentBuilder.toString();
    }
}
