module aerodynamics.weather.widget {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    opens com.aerodynamics.weather to javafx.fxml;
    exports com.aerodynamics.weather;
}