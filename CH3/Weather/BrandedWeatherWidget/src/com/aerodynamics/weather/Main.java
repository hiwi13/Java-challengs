package com.aerodynamics.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("weather_widget.fxml"));
        Parent root = loader.load();
        
        // Get controller and initialize data
        WeatherController controller = loader.getController();
        controller.initializeData();
        
        // Create scene
        Scene scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        
        // Configure stage
        primaryStage.setTitle("Aero Dynamics Weather Widget");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(550);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}