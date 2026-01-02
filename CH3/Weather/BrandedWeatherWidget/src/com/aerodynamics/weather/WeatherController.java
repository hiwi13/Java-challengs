package com.aerodynamics.weather;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class WeatherController implements Initializable {
    
    // Data properties for binding
    private StringProperty cityName = new SimpleStringProperty("");
    private StringProperty temperature = new SimpleStringProperty("");
    private StringProperty weatherDescription = new SimpleStringProperty("");
    private StringProperty flightCondition = new SimpleStringProperty("");
    private StringProperty windInfo = new SimpleStringProperty("");
    private StringProperty visibility = new SimpleStringProperty("");
    
    // Forecast properties
    private StringProperty day1Temp = new SimpleStringProperty("");
    private StringProperty day1Condition = new SimpleStringProperty("");
    private StringProperty day1Wind = new SimpleStringProperty("");
    private StringProperty day2Temp = new SimpleStringProperty("");
    private StringProperty day2Condition = new SimpleStringProperty("");
    private StringProperty day2Wind = new SimpleStringProperty("");
    private StringProperty day3Temp = new SimpleStringProperty("");
    private StringProperty day3Condition = new SimpleStringProperty("");
    private StringProperty day3Wind = new SimpleStringProperty("");
    
    // FXML Elements
    @FXML private BorderPane rootPane;
    
    // Top Section
    @FXML private Label cityLabel;
    @FXML private TextField cityInput;
    @FXML private Button refreshButton;
    
    // Center Section
    @FXML private Label temperatureLabel;
    @FXML private Label descriptionLabel;
    @FXML private Polygon aircraftShape;
    @FXML private Label windValueLabel;
    @FXML private Label visibilityValueLabel;
    
    // Right Section
    @FXML private VBox rightPanel;
    @FXML private Label conditionIndicator;
    
    // Bottom Section
    @FXML private HBox forecastContainer;
    @FXML private Label day1TempLabel;
    @FXML private Label day1ConditionLabel;
    @FXML private Label day1WindLabel;
    @FXML private Label day2TempLabel;
    @FXML private Label day2ConditionLabel;
    @FXML private Label day2WindLabel;
    @FXML private Label day3TempLabel;
    @FXML private Label day3ConditionLabel;
    @FXML private Label day3WindLabel;
    
    // Animation
    private Timeline aircraftAnimation;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind UI elements to properties
        bindUIElements();
        
        // Configure event handlers
        setupEventHandlers();
        
        // Initialize with sample data
        initializeSampleData();
    }
    
    public void initializeData() {
        // Additional initialization if needed
    }
    
    private void bindUIElements() {
        // Bind labels to properties
        cityLabel.textProperty().bind(cityName);
        temperatureLabel.textProperty().bind(temperature);
        descriptionLabel.textProperty().bind(weatherDescription);
        conditionIndicator.textProperty().bind(flightCondition);
        windValueLabel.textProperty().bind(windInfo);
        visibilityValueLabel.textProperty().bind(visibility);
        
        // Bind forecast labels
        day1TempLabel.textProperty().bind(day1Temp);
        day1ConditionLabel.textProperty().bind(day1Condition);
        day1WindLabel.textProperty().bind(day1Wind);
        day2TempLabel.textProperty().bind(day2Temp);
        day2ConditionLabel.textProperty().bind(day2Condition);
        day2WindLabel.textProperty().bind(day2Wind);
        day3TempLabel.textProperty().bind(day3Temp);
        day3ConditionLabel.textProperty().bind(day3Condition);
        day3WindLabel.textProperty().bind(day3Wind);
        
        // Bind button disable property to input field
        refreshButton.disableProperty().bind(cityInput.textProperty().isEmpty());
        
        // Bind condition indicator styling
        flightCondition.addListener((obs, oldVal, newVal) -> {
            updateConditionIndicatorStyle(newVal);
        });
    }
    
    private void setupEventHandlers() {
        // Refresh button action
        refreshButton.setOnAction(e -> handleRefresh());
        
        // Enter key in text field
        cityInput.setOnAction(e -> handleRefresh());
        
        // Initialize aircraft shape if not done in FXML
        if (aircraftShape == null || aircraftShape.getPoints().isEmpty()) {
            initializeAircraftShape();
        }
        
        // Setup animation
        setupAircraftAnimation();
    }
    
    private void initializeAircraftShape() {
        // Create aircraft polygon programmatically if not in FXML
        aircraftShape = new Polygon();
        aircraftShape.getPoints().addAll(
            0.0, -15.0,    // nose
            50.0, -8.0,    // right wingtip
            50.0, 0.0,     // right wing root
            25.0, 0.0,     // cockpit front
            25.0, 15.0,    // tail bottom
            0.0, 20.0,     // tail
            -25.0, 15.0,   // tail bottom left
            -25.0, 0.0,    // cockpit front left
            -50.0, 0.0,    // left wing root
            -50.0, -8.0    // left wingtip
        );
        aircraftShape.getStyleClass().add("aircraft-shape");
        
        // Add to center section (you'll need to adjust your FXML structure)
        // This is just a fallback if FXML doesn't contain the shape
    }
    
    private void setupAircraftAnimation() {
        if (aircraftShape == null) return;
        
        aircraftAnimation = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(aircraftShape.translateYProperty(), 0),
                new KeyValue(aircraftShape.rotateProperty(), 0)
            ),
            new KeyFrame(Duration.seconds(2),
                new KeyValue(aircraftShape.translateYProperty(), -10),
                new KeyValue(aircraftShape.rotateProperty(), 3)
            ),
            new KeyFrame(Duration.seconds(4),
                new KeyValue(aircraftShape.translateYProperty(), 0),
                new KeyValue(aircraftShape.rotateProperty(), 0)
            )
        );
        aircraftAnimation.setCycleCount(Timeline.INDEFINITE);
        aircraftAnimation.setAutoReverse(true);
        aircraftAnimation.play();
    }
    
    private void handleRefresh() {
        String location = cityInput.getText().trim();
        if (!location.isEmpty()) {
            updateWeatherData(location);
            
            // Restart animation for visual feedback
            if (aircraftAnimation != null) {
                aircraftAnimation.stop();
                aircraftAnimation.play();
            }
        }
    }
    
    private void updateWeatherData(String location) {
        // Update city name
        cityName.set(location.toUpperCase() + " (Airport)");
        
        // Simulate API response based on location
        if (location.toLowerCase().contains("jfk") || location.toLowerCase().contains("new york")) {
            setNewYorkWeather();
        } else if (location.toLowerCase().contains("ord") || location.toLowerCase().contains("chicago")) {
            setChicagoWeather();
        } else if (location.toLowerCase().contains("lax") || location.toLowerCase().contains("los angeles")) {
            setLosAngelesWeather();
        } else if (location.toLowerCase().contains("lhr") || location.toLowerCase().contains("london")) {
            setLondonWeather();
        } else {
            setDefaultWeather();
        }
    }
    
    private void setNewYorkWeather() {
        temperature.set("48°F / 9°C");
        weatherDescription.set("Clear Skies, Good Visibility");
        flightCondition.set("GOOD");
        windInfo.set("280° at 12 knots");
        visibility.set("10+ miles");
        
        // Forecast data
        day1Temp.set("52°F");
        day1Condition.set("Clear");
        day1Wind.set("12kt NW");
        
        day2Temp.set("48°F");
        day2Condition.set("Partly Cloudy");
        day2Wind.set("15kt W");
        
        day3Temp.set("55°F");
        day3Condition.set("Light Rain");
        day3Wind.set("8kt NE");
    }
    
    private void setChicagoWeather() {
        temperature.set("42°F / 6°C");
        weatherDescription.set("Partly Cloudy, Gusty");
        flightCondition.set("MARGINAL");
        windInfo.set("310° at 18 knots");
        visibility.set("6 miles");
        
        day1Temp.set("45°F");
        day1Condition.set("Cloudy");
        day1Wind.set("18kt NW");
        
        day2Temp.set("40°F");
        day2Condition.set("Snow Showers");
        day2Wind.set("22kt N");
        
        day3Temp.set("38°F");
        day3Condition.set("Freezing Rain");
        day3Wind.set("15kt NE");
    }
    
    private void setLosAngelesWeather() {
        temperature.set("68°F / 20°C");
        weatherDescription.set("Sunny, Clear");
        flightCondition.set("GOOD");
        windInfo.set("180° at 5 knots");
        visibility.set("15+ miles");
        
        day1Temp.set("70°F");
        day1Condition.set("Sunny");
        day1Wind.set("5kt SW");
        
        day2Temp.set("72°F");
        day2Condition.set("Mostly Sunny");
        day2Wind.set("8kt W");
        
        day3Temp.set("69°F");
        day3Condition.set("Clear");
        day3Wind.set("6kt NW");
    }
    
    private void setLondonWeather() {
        temperature.set("46°F / 8°C");
        weatherDescription.set("Light Fog, Drizzle");
        flightCondition.set("POOR");
        windInfo.set("240° at 8 knots");
        visibility.set("2 miles");
        
        day1Temp.set("48°F");
        day1Condition.set("Foggy");
        day1Wind.set("8kt SW");
        
        day2Temp.set("50°F");
        day2Condition.set("Light Rain");
        day2Wind.set("12kt W");
        
        day3Temp.set("47°F");
        day3Condition.set("Drizzle");
        day3Wind.set("10kt NW");
    }
    
    private void setDefaultWeather() {
        temperature.set("55°F / 13°C");
        weatherDescription.set("Clear and Calm");
        flightCondition.set("GOOD");
        windInfo.set("360° at 5 knots");
        visibility.set("10+ miles");
        
        day1Temp.set("56°F");
        day1Condition.set("Clear");
        day1Wind.set("6kt N");
        
        day2Temp.set("58°F");
        day2Condition.set("Partly Cloudy");
        day2Wind.set("8kt E");
        
        day3Temp.set("54°F");
        day3Condition.set("Clear");
        day3Wind.set("7kt SE");
    }
    
    private void updateConditionIndicatorStyle(String condition) {
        // Clear existing condition classes
        conditionIndicator.getStyleClass().removeAll(
            "condition-good", "condition-marginal", "condition-poor"
        );
        
        // Add appropriate class based on condition
        switch(condition.toUpperCase()) {
            case "GOOD":
                conditionIndicator.getStyleClass().add("condition-good");
                break;
            case "MARGINAL":
                conditionIndicator.getStyleClass().add("condition-marginal");
                break;
            case "POOR":
                conditionIndicator.getStyleClass().add("condition-poor");
                break;
            default:
                conditionIndicator.getStyleClass().add("condition-marginal");
        }
    }
    
    private void initializeSampleData() {
        // Set initial data
        setNewYorkWeather();
        cityName.set("KJFK - New York JFK International");
        cityInput.setText("KJFK");
    }
    
    // Getter methods for properties (if needed elsewhere)
    public StringProperty cityNameProperty() { return cityName; }
    public StringProperty temperatureProperty() { return temperature; }
    public StringProperty weatherDescriptionProperty() { return weatherDescription; }
    public StringProperty flightConditionProperty() { return flightCondition; }
    public StringProperty windInfoProperty() { return windInfo; }
    public StringProperty visibilityProperty() { return visibility; }
}