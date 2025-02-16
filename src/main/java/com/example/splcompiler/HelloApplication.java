package com.example.splcompiler;

import javafx.application.Application;

import javafx.stage.Stage;

import ui.dialogs.StartWindowDialog;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       // SplPrime.main(new String[0]);
       /* MainWindow mainWindow = MainWindow.getInstance(new StyleFactory());
        mainWindow.prepareView();
        mainWindow.initComponents();
        mainWindow.showView();*/
        StartWindowDialog startWindowDialog = StartWindowDialog.getStartWindowDialogInstance();
        startWindowDialog.prepareView();
        startWindowDialog.initComponents();
        startWindowDialog.showView();
    }

    public static void main(String[] args) {
        launch();
    }
}