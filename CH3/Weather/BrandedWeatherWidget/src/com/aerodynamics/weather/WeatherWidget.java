package com.aerodynamics.weather;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.beans.property.*;

public class WeatherWidget extends Application {
    
    // Properties for data binding
    private StringProperty cityName = new SimpleStringProperty("");
    private StringProperty temperature = new SimpleStringProperty("");
    private StringProperty weatherDescription = new SimpleStringProperty("");
    private StringProperty flightCondition = new SimpleStringProperty("");
    private StringProperty windInfo = new SimpleStringProperty("");
    private StringProperty visibility = new SimpleStringProperty("");
    
    // Animation timeline
    private Timeline aircraftAnimation;
    
    @Override
    public void start(Stage primaryStage) {
        // Root layout - BorderPane
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");
        
        // TOP: City name and controls
        VBox topSection = createTopSection();
        root.setTop(topSection);
        
        // CENTER: Main weather information
        VBox centerSection = createCenterSection();
        root.setCenter(centerSection);
        
        // RIGHT: Flight conditions panel
        VBox rightSection = createRightSection();
        root.setRight(rightSection);
        
        // BOTTOM: Forecast section
        HBox bottomSection = createBottomSection();
        root.setBottom(bottomSection);
        
        // Create scene and apply CSS
        Scene scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        
        primaryStage.setTitle("Aero Dynamics Aviation Weather Widget");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initialize with sample data
        initializeSampleData();
    }
    
    private VBox createTopSection() {
        VBox topBox = new VBox(10);
        topBox.getStyleClass().add("top-section");
        topBox.setPadding(new Insets(15));
        
        // City label (large heading)
        Label cityLabel = new Label();
        cityLabel.getStyleClass().add("city-label");
        cityLabel.textProperty().bind(cityName);
        
        // Input and refresh controls
        HBox controlsBox = new HBox(10);
        controlsBox.setAlignment(Pos.CENTER);
        
        TextField cityInput = new TextField();
        cityInput.setPromptText("Enter airport code or city");
        cityInput.getStyleClass().add("city-input");
        
        Button refreshButton = new Button("Refresh");
        refreshButton.getStyleClass().add("refresh-button");
        
        // Bind refresh button disable property
        refreshButton.disableProperty().bind(cityInput.textProperty().isEmpty());
        
        // Refresh action
        refreshButton.setOnAction(e -> {
            if (!cityInput.getText().isEmpty()) {
                updateWeatherData(cityInput.getText());
            }
        });
        
        controlsBox.getChildren().addAll(cityInput, refreshButton);
        topBox.getChildren().addAll(cityLabel, controlsBox);
        
        return topBox;
    }
    
    private VBox createCenterSection() {
        VBox centerBox = new VBox(15);
        centerBox.getStyleClass().add("center-section");
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.CENTER);
        
        // Temperature display
        Label tempLabel = new Label();
        tempLabel.getStyleClass().add("temperature-label");
        tempLabel.textProperty().bind(temperature);
        
        // Weather description
        Label descLabel = new Label();
        descLabel.getStyleClass().add("description-label");
        descLabel.textProperty().bind(weatherDescription);
        
        // Aircraft shape
        Polygon aircraft = createAircraftShape();
        aircraft.getStyleClass().add("aircraft-shape");
        
        // Animation for aircraft
        setupAircraftAnimation(aircraft);
        
        // Critical flight data grid
        GridPane flightData = createFlightDataGrid();
        
        centerBox.getChildren().addAll(tempLabel, descLabel, aircraft, flightData);
        
        return centerBox;
    }
    
    private Polygon createAircraftShape() {
        // Create aircraft polygon (simplified aircraft shape)
        Polygon aircraft = new Polygon();
        aircraft.getPoints().addAll(
            0.0, -10.0,    // nose
            40.0, -5.0,    // right wingtip
            40.0, 0.0,     // right wing root
            20.0, 0.0,     // cockpit front
            20.0, 10.0,    // tail bottom
            0.0, 15.0,     // tail
            -20.0, 10.0,   // tail bottom left
            -20.0, 0.0,    // cockpit front left
            -40.0, 0.0,    // left wing root
            -40.0, -5.0    // left wingtip
        );
        
        return aircraft;
    }
    
    private void setupAircraftAnimation(Polygon aircraft) {
        aircraftAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, 
                new KeyValue(aircraft.translateYProperty(), 0),
                new KeyValue(aircraft.rotateProperty(), 0)
            ),
            new KeyFrame(Duration.seconds(1.5),
                new KeyValue(aircraft.translateYProperty(), -8),
                new KeyValue(aircraft.rotateProperty(), 2)
            ),
            new KeyFrame(Duration.seconds(3),
                new KeyValue(aircraft.translateYProperty(), 0),
                new KeyValue(aircraft.rotateProperty(), 0)
            ),
            new KeyFrame(Duration.seconds(4.5),
                new KeyValue(aircraft.translateYProperty(), 8),
                new KeyValue(aircraft.rotateProperty(), -2)
            ),
            new KeyFrame(Duration.seconds(6),
                new KeyValue(aircraft.translateYProperty(), 0),
                new KeyValue(aircraft.rotateProperty(), 0)
            )
        );
        aircraftAnimation.setCycleCount(Timeline.INDEFINITE);
        aircraftAnimation.setAutoReverse(true);
        aircraftAnimation.play();
    }
    
    private GridPane createFlightDataGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("flight-data-grid");
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 0, 0, 0));
        
        // Wind information
        Label windTitle = new Label("WIND:");
        windTitle.getStyleClass().add("data-title");
        
        Label windValue = new Label();
        windValue.getStyleClass().add("data-value");
        windValue.textProperty().bind(windInfo);
        
        // Visibility
        Label visTitle = new Label("VISIBILITY:");
        visTitle.getStyleClass().add("data-title");
        
        Label visValue = new Label();
        visValue.getStyleClass().add("data-value");
        visValue.textProperty().bind(visibility);
        
        // Add to grid
        grid.add(windTitle, 0, 0);
        grid.add(windValue, 1, 0);
        grid.add(visTitle, 0, 1);
        grid.add(visValue, 1, 1);
        
        return grid;
    }
    
    private VBox createRightSection() {
        VBox rightBox = new VBox(10);
        rightBox.getStyleClass().add("right-section");
        rightBox.setPadding(new Insets(20, 15, 20, 15));
        rightBox.setMinWidth(120);
        
        Label conditionsTitle = new Label("FLIGHT CONDITIONS");
        conditionsTitle.getStyleClass().add("conditions-title");
        
        Label conditionIndicator = new Label();
        conditionIndicator.getStyleClass().add("condition-indicator");
        conditionIndicator.textProperty().bind(flightCondition);
        
        // Bind style class based on condition
        flightCondition.addListener((obs, oldVal, newVal) -> {
            conditionIndicator.getStyleClass().removeAll("condition-good", "condition-marginal", "condition-poor");
            switch(newVal) {
                case "GOOD":
                    conditionIndicator.getStyleClass().add("condition-good");
                    break;
                case "MARGINAL":
                    conditionIndicator.getStyleClass().add("condition-marginal");
                    break;
                case "POOR":
                    conditionIndicator.getStyleClass().add("condition-poor");
                    break;
            }
        });
        
        // Additional info
        Label infoLabel = new Label("• Turbulence: Light\n• Icing: None\n• Ceiling: 5000ft");
        infoLabel.getStyleClass().add("flight-info");
        
        rightBox.getChildren().addAll(conditionsTitle, conditionIndicator, infoLabel);
        
        return rightBox;
    }
    
    private HBox createBottomSection() {
        HBox bottomBox = new HBox(15);
        bottomBox.getStyleClass().add("bottom-section");
        bottomBox.setPadding(new Insets(15));
        bottomBox.setAlignment(Pos.CENTER);
        
        // Create 3 forecast days
        for (int i = 0; i < 3; i++) {
            VBox forecastDay = createForecastDay(i);
            bottomBox.getChildren().add(forecastDay);
        }
        
        return bottomBox;
    }
    
    private VBox createForecastDay(int dayOffset) {
        VBox dayBox = new VBox(8);
        dayBox.getStyleClass().add("forecast-day");
        dayBox.setPadding(new Insets(10));
        dayBox.setAlignment(Pos.CENTER);
        
        String[] days = {"TODAY", "TOMORROW", "DAY 3"};
        String[] temps = {"52°F", "48°F", "55°F"};
        String[] conditions = {"Clear", "Partly Cloudy", "Light Rain"};
        String[] winds = {"12kt NW", "15kt W", "8kt NE"};
        
        Label dayLabel = new Label(days[dayOffset]);
        dayLabel.getStyleClass().add("forecast-day-label");
        
        Label tempLabel = new Label(temps[dayOffset]);
        tempLabel.getStyleClass().add("forecast-temp");
        
        Label condLabel = new Label(conditions[dayOffset]);
        condLabel.getStyleClass().add("forecast-condition");
        
        Label windLabel = new Label(winds[dayOffset]);
        windLabel.getStyleClass().add("forecast-wind");
        
        dayBox.getChildren().addAll(dayLabel, tempLabel, condLabel, windLabel);
        
        return dayBox;
    }
    
    private void initializeSampleData() {
        cityName.set("KJFK - New York");
        temperature.set("48°F / 9°C");
        weatherDescription.set("Clear Skies, Good Visibility");
        flightCondition.set("GOOD");
        windInfo.set("280° at 12 knots");
        visibility.set("10+ miles");
    }
    
    private void updateWeatherData(String location) {
        // In a real app, this would fetch from an API
        // For demo, we'll just update with sample data
        cityName.set(location.toUpperCase());
        
        // Simulate different conditions based on input
        if (location.toLowerCase().contains("chi") || location.contains("ORD")) {
            temperature.set("42°F / 6°C");
            weatherDescription.set("Partly Cloudy");
            flightCondition.set("MARGINAL");
            windInfo.set("310° at 18 knots");
            visibility.set("6 miles");
        } else if (location.toLowerCase().contains("lon") || location.contains("LHR")) {
            temperature.set("46°F / 8°C");
            weatherDescription.set("Light Fog");
            flightCondition.set("POOR");
            windInfo.set("240° at 8 knots");
            visibility.set("2 miles");
        } else {
            temperature.set("55°F / 13°C");
            weatherDescription.set("Clear and Calm");
            flightCondition.set("GOOD");
            windInfo.set("360° at 5 knots");
            visibility.set("10+ miles");
        }
        
        // Restart animation for visual feedback
        if (aircraftAnimation != null) {
            aircraftAnimation.stop();
            aircraftAnimation.play();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}