package commands.config;

import commands.Command;
import model.languages.Language;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 *
 *
 * @author Maurice Amon
 */

public class ParseConfigFileCommand implements Command {

    private String configFile;

    private final String configFileName = "config.splc";

    private final String LANGUAGE_NAME = "LANGUAGE";

    private final String MAIN_FILE_VAR_NAME = "MAIN_FILE";

    private final String DESIGN = "DESIGN";

    private String languageName;

    private String mainFile;

    private String design;

    private Boolean isValidConfig = Boolean.TRUE;
    

    public ParseConfigFileCommand(File choosenDir) {
        for(File file : choosenDir.listFiles()) {
            if(file.getName().equals(configFileName)) {
                try {
                    Scanner scanner = new Scanner(file);
                    StringBuilder sb = new StringBuilder();
                    while(scanner.hasNext()) {
                        sb.append(scanner.next() + "\n");
                    }
                    scanner.close();
                    this.configFile = sb.toString();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void execute() {
        String[] configLines = configFile.split("\n");
        for (String line : configLines) {
            String[] config = line.split("=");
            switch (config[0]) {
                case LANGUAGE_NAME:
                    languageName = config[1];
                    if(!Language.ALL_LANGUAGES.contains(languageName)) {
                        isValidConfig = Boolean.FALSE;
                    }
                    break;
                case MAIN_FILE_VAR_NAME:
                    mainFile = config[1];
                    if(!Files.exists(Path.of(mainFile))) {
                        isValidConfig = Boolean.FALSE;
                    }
                    break;
                case DESIGN:
                    design = config[1];
                    break;

            }
        }
    }

    @Override
    public void undo() {
        // No implementation yet ..
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getMainFile() {
        return mainFile;
    }

    public String getDesign() {
        return design;
    }

    public Boolean getValidConfig() {
        return isValidConfig;
    }
}
