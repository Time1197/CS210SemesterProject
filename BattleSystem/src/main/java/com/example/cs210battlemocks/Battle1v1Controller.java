package com.example.cs210battlemocks;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class Battle1v1Controller {

    @FXML private Label menuLabel;
    @FXML private Button attackButton;
    @FXML private Button abilityButton;
    @FXML private Button itemButton;
    @FXML private Button fleeButton;
    @FXML private AnchorPane root;

    @FXML private ProgressBar playerHP1;
    @FXML private ProgressBar playerHP2;
    @FXML private ProgressBar enemyHP;

    @FXML
    public void initialize() {
        // Make root focusable so keyboard works
        root.setFocusTraversable(true);
        root.requestFocus();

        root.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
    }

    @FXML
    private void onAction(javafx.event.ActionEvent event) {
        if (event.getSource() == attackButton) attack();
        else if (event.getSource() == abilityButton) ability();
        else if (event.getSource() == itemButton) item();
        else if (event.getSource() == fleeButton) flee();
    }

    private void attack() {
        menuLabel.setText("Attack selected!");
        if (enemyHP != null) {
            enemyHP.setProgress(Math.max(0, enemyHP.getProgress() - 0.1));
        }
    }

    private void ability() {
        menuLabel.setText("Ability selected!");
    }

    private void item() {
        menuLabel.setText("Item selected!");
    }

    private void flee() {
        menuLabel.setText("You fled!");
    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode key = event.getCode();
        switch (key) {
            case A -> attack();
            case S -> ability();
            case D -> item();
            case F -> flee();
            default -> {}
        }
    }
}

