module org.example.personaldiarymanagergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jdk.jsobject;

    opens org.example.personaldiarymanagergui to javafx.fxml;
    exports org.example.personaldiarymanagergui;
}
