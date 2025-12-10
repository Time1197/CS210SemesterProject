package com.example.cs210battlemocks;


import javafx.application.Platform;
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

    private double player1Health = 1.0;
    private double player2Health = 1.0;
    private double enemyHealth = 1.0;

    private boolean playerTurn = true;

    @FXML
    public void initialize() {
        root.setFocusTraversable(true);
        root.requestFocus();
        root.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);

        updateEnemyHighlight();
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

        // Player attacks enemy
        enemyHealth = Math.max(0, enemyHealth - 0.2);
        enemyHP.setProgress(enemyHealth);
        menuLabel.setText("You attacked the enemy!");

        if (enemyHealth <= 0) {
            menuLabel.setText("You defeated the enemy!");
            disablePlayerActions();
            return;
        }

        playerTurn = false;
        startEnemyTurn();
    }

    private void ability() { menuLabel.setText("Ability selected! (not implemented)"); }
    private void item() { menuLabel.setText("Item selected! (not implemented)"); }
    private void flee() { menuLabel.setText("You fled!"); disablePlayerActions(); }

    // =================== ENEMY TURN ===================
    private void startEnemyTurn() {
        menuLabel.setText("Enemy's turn...");

        new Thread(() -> {
            try {
                Thread.sleep(700); // delay for effect
                enemyAttack();

                Thread.sleep(700); // another delay if you want multiple attacks
            } catch (InterruptedException ignored) {}

            playerTurn = true;
            Platform.runLater(() -> menuLabel.setText("Your turn"));
        }).start();
    }

    private void enemyAttack() {
        if (enemyHealth <= 0) return;

        Platform.runLater(() -> {
            // Randomly pick a player to attack
            boolean hitPlayer1 = Math.random() < 0.5;

            if (hitPlayer1 && player1Health > 0) {
                double damage = Math.random() * 0.2 + 0.05; // 5-25%
                player1Health = Math.max(0, player1Health - damage);
                playerHP1.setProgress(player1Health);
                menuLabel.setText(String.format("Enemy hit Player 1 for %.0f%%!", damage * 100));
            } else if (player2Health > 0) {
                double damage = Math.random() * 0.2 + 0.05; // 5-25%
                player2Health = Math.max(0, player2Health - damage);
                playerHP2.setProgress(player2Health);
                menuLabel.setText(String.format("Enemy hit Player 2 for!", damage * 100));
            }

            // Check for defeat
            if (player1Health <= 0 && player2Health <= 0) {
                menuLabel.setText("All players defeated!");
                disablePlayerActions();
            }
        });
    }

    // =================== DISABLE PLAYER BUTTONS ===================
    private void disablePlayerActions() {
        attackButton.setDisable(true);
        abilityButton.setDisable(true);
        itemButton.setDisable(true);
        fleeButton.setDisable(true);
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
            case LEFT, RIGHT -> updateEnemyHighlight();
            default -> {}
        }
    }

    // =================== HIGHLIGHT ENEMY ===================
    private void updateEnemyHighlight() {
        enemyHP.setStyle("-fx-accent: orange;"); // highlight enemy
    }
}
