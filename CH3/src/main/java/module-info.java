module org.example.brandedweatherwidget {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens org.example.brandedweatherwidget to javafx.fxml;
    exports org.example.brandedweatherwidget;
    exports ecolife;
}