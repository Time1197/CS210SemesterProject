package com.example.cs210battlemocks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Battle2v1Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
