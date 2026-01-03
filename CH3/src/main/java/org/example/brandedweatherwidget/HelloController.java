package org.example.brandedweatherwidget;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

public class HelloController {
    @FXML
    private TextField cityTextField;
    @FXML
    private Label cityLabel;
    @FXML
    private Label tempLabel;
    @FXML
    private Label conditionLabel;
    @FXML
    private Label detailsLabel;
    @FXML
    private Button searchButton;
    @FXML
    private HBox forecastBox;

    private static final String API_KEY = "9e1dc5cd71b67bc9790decaf552bf82c";
    private static final String CURRENT_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    private static final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric";

    @FXML
    public void initialize() {
        // Disable Search button if city TextField is empty
        searchButton.disableProperty().bind(cityTextField.textProperty().isEmpty());
    }

    @FXML
    protected void onSearchButtonClick() {
        String city = cityTextField.getText();
        if (city != null && !city.isEmpty()) {
            fetchWeatherData(city);
            fetchForecastData(city);
        }
    }

    private void fetchWeatherData(String city) {
        CompletableFuture.runAsync(() -> {
            try {
                String response = makeApiCall(String.format(CURRENT_WEATHER_URL, city, API_KEY));
                if (response != null) {
                    JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                    updateCurrentWeatherUI(jsonResponse);
                } else {
                    Platform.runLater(() -> {
                        cityLabel.setText("City not found");
                        tempLabel.setText("--°C");
                        conditionLabel.setText("");
                        detailsLabel.setText("");
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> cityLabel.setText("Error fetching data"));
            }
        });
    }

    private void fetchForecastData(String city) {
        CompletableFuture.runAsync(() -> {
            try {
                String response = makeApiCall(String.format(FORECAST_URL, city, API_KEY));
                if (response != null) {
                    JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                    updateForecastUI(jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String makeApiCall(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateCurrentWeatherUI(JsonObject jsonResponse) {
        String cityName = jsonResponse.get("name").getAsString();
        String country = jsonResponse.getAsJsonObject("sys").get("country").getAsString();
        JsonObject main = jsonResponse.getAsJsonObject("main");
        double temp = main.get("temp").getAsDouble();
        double humidity = main.get("humidity").getAsDouble();
        double windSpeed = jsonResponse.getAsJsonObject("wind").get("speed").getAsDouble();
        
        JsonObject weather = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject();
        String description = weather.get("description").getAsString();

        Platform.runLater(() -> {
            cityLabel.setText(cityName + ", " + country);
            tempLabel.setText(String.format("%.1f°C", temp));
            conditionLabel.setText(description.substring(0, 1).toUpperCase() + description.substring(1));
            detailsLabel.setText(String.format("Humidity: %.0f%%  Wind: %.2f m/s", humidity, windSpeed));
        });
    }

    private void updateForecastUI(JsonObject jsonResponse) {
        JsonArray list = jsonResponse.getAsJsonArray("list");
        Platform.runLater(() -> {
            forecastBox.getChildren().clear();
            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE").withZone(ZoneId.systemDefault());

            // The API returns data every 3 hours. We'll pick one data point per day (approx every 8th item)
            for (int i = 7; i < list.size(); i += 8) {
                if (forecastBox.getChildren().size() >= 5) break;

                JsonObject item = list.get(i).getAsJsonObject();
                long dt = item.get("dt").getAsLong();
                double temp = item.getAsJsonObject("main").get("temp").getAsDouble();
                String desc = item.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
                
                String dayName = dayFormatter.format(Instant.ofEpochSecond(dt));

                VBox dayBox = new VBox();
                dayBox.getStyleClass().add("forecast-day-card");
                
                Label dayLabel = new Label(dayName);
                dayLabel.getStyleClass().add("forecast-day-title");
                
                Label tempLabel = new Label(String.format("%.0f°C", temp));
                tempLabel.getStyleClass().add("forecast-temp");
                
                Label descLabel = new Label(desc);
                descLabel.getStyleClass().add("forecast-desc");
                
                dayBox.getChildren().addAll(dayLabel, tempLabel, descLabel);
                forecastBox.getChildren().add(dayBox);
            }
        });
    }
}
