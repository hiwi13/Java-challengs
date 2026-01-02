package org.example.personaldiarymanagergui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DiaryController {

    @FXML
    private StackPane mainContainer;

    @FXML
    private WebView entryViewer;

    @FXML
    private VBox editorContainer;

    @FXML
    private HTMLEditor entryEditor;

    @FXML
    private TextField entryTitleField;

    private Timeline autoSaveTimeline;
    private int currentEditingId = -1; // -1 means new entry

    @FXML
    public void initialize() {
        // Load the HTML Dashboard
        URL url = getClass().getResource("DiaryUI/index.html");
        if (url != null) {
            WebEngine engine = entryViewer.getEngine();
            engine.load(url.toExternalForm());

            // Set up communication between Java and JavaScript
            engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    JSObject window = (JSObject) engine.executeScript("window");
                    window.setMember("diaryApp", this);
                }
            });
        }

        setupAutoSave();
    }

    private void setupAutoSave() {
        autoSaveTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> saveEntry()));
        autoSaveTimeline.setCycleCount(Timeline.INDEFINITE);
        autoSaveTimeline.play();
    }

    // This method will be called from JavaScript to create a new entry
    public void openNewEntryEditor() {
        javafx.application.Platform.runLater(() -> {
            currentEditingId = -1;
            entryTitleField.setText("");
            entryEditor.setHtmlText("");
            editorContainer.setVisible(true);
            entryViewer.setVisible(false);
        });
    }

    // This method will be called from JavaScript to edit an existing entry
    public void editEntry(int id, String title, String content) {
        javafx.application.Platform.runLater(() -> {
            currentEditingId = id;
            entryTitleField.setText(title);
            entryEditor.setHtmlText(content);
            editorContainer.setVisible(true);
            entryViewer.setVisible(false);
        });
    }

    @FXML
    protected void onSaveButtonClick() {
        saveEntry();
        
        // Pass the saved data back to JavaScript to update the UI
        String title = entryTitleField.getText();
        // Escape single quotes for JS string
        String safeTitle = title.replace("'", "\\'");
        // We need to be careful with HTML content passing back to JS. 
        // For simplicity, we'll just reload the page or update the specific entry if possible.
        // A better approach is to have a robust data layer.
        
        // For this demo, we will just reload the page to reset state, 
        // but in a real app we would update the JS model.
        // Let's try to update the JS model directly.
        
        String content = entryEditor.getHtmlText();
        // Basic escaping for JS
        String safeContent = content.replace("'", "\\'").replace("\n", "\\n").replace("\r", "");

        javafx.application.Platform.runLater(() -> {
            editorContainer.setVisible(false);
            entryViewer.setVisible(true);
            
            if (currentEditingId == -1) {
                // New Entry - we would need to generate an ID and add it
                // For now, let's just reload to keep it simple as we don't have a persistent backend
                entryViewer.getEngine().reload();
            } else {
                // Update existing entry
                entryViewer.getEngine().executeScript("updateEntry(" + currentEditingId + ", '" + safeTitle + "', '" + safeContent + "')");
            }
        });
    }

    @FXML
    protected void onCancelButtonClick() {
        editorContainer.setVisible(false);
        entryViewer.setVisible(true);
    }

    private void saveEntry() {
        try {
            // In a real app, save to database or file system based on ID
            String filename = (currentEditingId == -1) ? "new-entry.html" : "entry-" + currentEditingId + ".html";
            Files.write(Paths.get(filename), entryEditor.getHtmlText().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
