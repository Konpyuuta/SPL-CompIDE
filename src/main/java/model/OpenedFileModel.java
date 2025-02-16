package model;

import splprime.Observable;
import splprime.Observer;
import ui.MainWindow;
import ui.components.MultiTabEditor;
import ui.components.editor.Editor;
import ui.components.editor.StandardEditor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Maurice Amon
 */
public class OpenedFileModel extends Observable {

    private Integer id;

    private String fileName;

    private String path;

    private String content;

    public static OpenedFileModel openedFileModel;

    private static AtomicInteger idCounter = new AtomicInteger(0);

    public OpenedFileModel(String fileName, String path, String content) {
        this.fileName = fileName;
        this.path = path;
        this.content = content;
        generateUID();
        addObserver((Observer)MainWindow.getInstance(null).toolBar);
        notifyObservers(Notifications.LOADED);
    }

    private void generateUID() {
        id = idCounter.getAndIncrement();
    }

    public static OpenedFileModel createNewInstance(String fileName, String path, String content) {
        openedFileModel = new OpenedFileModel(fileName, path, content);
        return openedFileModel;
    }

    public void destroyInstance() {
        openedFileModel = null;
        notifyObservers(Notifications.CLOSED);
    }

    public static OpenedFileModel createNewInstance() {
        return openedFileModel;
    }

    public Integer getId() {
        return id;
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
        content = ((Editor)MultiTabEditor.getInstance().getEditorTabByID(id).getContent()).getText();
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
