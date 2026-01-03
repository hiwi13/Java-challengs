package org.example.brandedweatherwidget;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ImageViewer extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the image from the resources folder
        // Ensure "forest.jpg" is in src/main/resources/
        Image image = new Image(getClass().getResource("/forest.jpg").toExternalForm());

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800); // Adjust size as needed

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Image Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
