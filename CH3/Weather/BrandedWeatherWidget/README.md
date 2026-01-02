# Aero Dynamics Weather Widget

## Features Implemented
1. **Brand-Aligned Design**: Aviation colors (navy, safety orange, runway greys)
2. **Technical Typography**: Roboto Mono for headings, Open Sans for body
3. **Critical Flight Data**: Wind speed/direction, visibility, flight conditions
4. **Interactive Elements**: 
   - Refresh button disabled when input is empty (property binding)
   - Animated aircraft shape
   - Color-coded flight conditions
5. **CSS Architecture**: External styling for easy rebranding

## How to Run
```bash
javac --module-path /path/to/javafx-sdk --add-modules javafx.controls,javafx.fxml *.java
java --module-path /path/to/javafx-sdk --add-modules javafx.controls,javafx.fxml com.aerodynamics.weather.WeatherWidget