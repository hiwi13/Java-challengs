package org.example.personaldiarymanagergui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DiaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DiaryApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Personal Diary Manager");
        stage.setScene(scene);
        stage.show();
    }
}
