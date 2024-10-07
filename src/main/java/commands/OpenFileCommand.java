package commands;

import model.OpenedFileModel;
import ui.components.SPLEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OpenFileCommand implements Command {

    private String fileName;

    private String absolutePath;

    public OpenFileCommand(String fileName, String absolutePath) {
        this.fileName = fileName;
        this.absolutePath = absolutePath;
    }

    @Override
    public void execute() {
        File file = new File(absolutePath);
        String content = readFile(file);
        OpenedFileModel model = OpenedFileModel.createNewInstance(fileName, absolutePath, content);
        SPLEditor.getInstance().clear();
        SPLEditor.getInstance().setText(model.getContent());
    }

    private String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                sb.append(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
