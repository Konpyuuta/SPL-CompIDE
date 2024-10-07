package model;

import splprime.Observable;
import splprime.Observer;
import ui.MainWindow;
import ui.components.SPLEditor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Maurice Amon
 */
public class OpenedFileModel extends Observable {

    private String fileName;

    private String path;

    private String content;

    public static OpenedFileModel openedFileModel;

    private OpenedFileModel(String fileName, String path, String content) {
        this.fileName = fileName;
        this.path = path;
        this.content = content;
        addObserver((Observer)MainWindow.getInstance(null).toolBar);
        notifyObservers("loaded");
    }

    public static OpenedFileModel createNewInstance(String fileName, String path, String content) {
        openedFileModel = new OpenedFileModel(fileName, path, content);
        return openedFileModel;
    }

    public void destroyInstance() {
        openedFileModel = null;
        notifyObservers("closed");
    }

    public static OpenedFileModel createNewInstance() {
        return openedFileModel;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }

    public void saveFile() {
        content = SPLEditor.getInstance().getText();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(path));
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
