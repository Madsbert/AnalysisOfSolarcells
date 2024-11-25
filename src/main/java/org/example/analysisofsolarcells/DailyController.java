package org.example.analysisofsolarcells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DailyController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}