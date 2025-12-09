package com.example.cs210battlemocks;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class Battle1v2Controller {

    @FXML private Label menuLabel;
    @FXML private Button attackButton;
    @FXML private Button abilityButton;
    @FXML private Button itemButton;
    @FXML private Button fleeButton;
    @FXML private AnchorPane root;

    @FXML private ProgressBar playerHP1;
    @FXML private ProgressBar playerHP2;
    @FXML private ProgressBar enemyHP1;
    @FXML private ProgressBar enemyHP2;

    private double player1HP = 1.0;
    private double player2HP = 1.0;

    private boolean playerTurn = true;

    // Track which enemy is selected (1 or 2)
    private int selectedTarget = 1;

    @FXML
    public void initialize() {
        root.setFocusTraversable(true);
        root.requestFocus();
        root.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);

        updateEnemyHighlight();
        menuLabel.setText("Your turn");
    }

    // =================== PLAYER ACTIONS ===================
    @FXML
    private void onAction(javafx.event.ActionEvent event) {
        if (!playerTurn) return;

        if (event.getSource() == attackButton) attack();
        else if (event.getSource() == abilityButton) ability();
        else if (event.getSource() == itemButton) item();
        else if (event.getSource() == fleeButton) flee();
    }

    private void attack() {
        if (!playerTurn) return;

        menuLabel.setText("You attacked!");

        ProgressBar target = (selectedTarget == 1) ? enemyHP1 : enemyHP2;
        if (target != null) {
            target.setProgress(Math.max(0, target.getProgress() - 0.1));
        }

        playerTurn = false;
        startEnemyTurn();
    }

    private void ability() { menuLabel.setText("Ability selected!"); }

    private void item() { menuLabel.setText("Item selected!"); }

    private void flee() { menuLabel.setText("You fled!"); }

    // =================== ENEMY TURN ===================
    private void startEnemyTurn() {
        menuLabel.setText("Enemy turn...");

        new Thread(() -> {
            try {
                Thread.sleep(700);
                enemyAttack(1);

                Thread.sleep(700);
                enemyAttack(2);

            } catch (InterruptedException ignored) {}

            playerTurn = true;
            Platform.runLater(() -> menuLabel.setText("Your turn"));
        }).start();
    }

    private void enemyAttack(int enemyNumber) {
        ProgressBar bar = (enemyNumber == 1) ? enemyHP1 : enemyHP2;
        if (bar.getProgress() <= 0) return;

        int target = (Math.random() < 0.5) ? 1 : 2;

        Platform.runLater(() -> {
            if (target == 1) {
                player1HP = Math.max(0, player1HP - 0.1);
                playerHP1.setProgress(player1HP);
                menuLabel.setText("Enemy " + enemyNumber + " hit Player 1!");
            } else {
                player2HP = Math.max(0, player2HP - 0.1);
                playerHP2.setProgress(player2HP);
                menuLabel.setText("Enemy " + enemyNumber + " hit Player 2!");
            }
        });
    }

    // =================== KEYBOARD INPUT ===================
    private void handleKeyPress(KeyEvent event) {
        if (!playerTurn) return;

        KeyCode key = event.getCode();

        switch (key) {
            case A -> attack();
            case S -> ability();
            case D -> item();
            case F -> flee();
            case LEFT -> {
                // Move selection left
                selectedTarget = (selectedTarget == 1) ? 2 : 1;
                updateEnemyHighlight();
            }
            case RIGHT -> {
                // Move selection right
                selectedTarget = (selectedTarget == 2) ? 1 : 2;
                updateEnemyHighlight();
            }
            default -> {}
        }
    }

    // =================== HIGHLIGHT SELECTED ENEMY ===================
    private void updateEnemyHighlight() {
        if (selectedTarget == 1) {
            enemyHP1.setStyle("-fx-accent: orange;");
            enemyHP2.setStyle("-fx-accent: red;");
        } else {
            enemyHP1.setStyle("-fx-accent: red;");
            enemyHP2.setStyle("-fx-accent: orange;");
        }
    }
}

