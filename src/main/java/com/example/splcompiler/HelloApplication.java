package com.example.splcompiler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import splprime.SplPrime;
import ui.MainWindow;
import ui.StyleFactory;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       // SplPrime.main(new String[0]);
        MainWindow mainWindow = MainWindow.getInstance(new StyleFactory());
        mainWindow.prepareView();
        mainWindow.initComponents();
        mainWindow.showView();
    }

    public static void main(String[] args) {
        launch();
    }
}