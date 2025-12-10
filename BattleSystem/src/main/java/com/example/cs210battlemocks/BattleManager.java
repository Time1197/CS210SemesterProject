package com.example.cs210battlemocks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
This is the battle engine and the main brain of the battle system. It should control the entire flow of combat.
It acts as a state machine. It uses custom states defined in the BattleState Enum file
HOW TO USE FOR UI:
1. Initialize a BattleManager object in HelloApplication with a list of Players and enemies
2. Call startBattle() to begin
3. When the player clicks attack, the attack logic should be called, and the damage is applied
4. Next call nextTurn()
Note: this class doesn't handle graphics! It only updates numbers. Use getBattleState() to evaluate what to do next
 */

public class BattleManager {
    //All entities in the fight
    private List<Character> combatants;
    //The "Deck" of turns. Who is at the top, goes first.
    private Queue<Character> turnOrder;
    //Tracks who's turn it is (PLAYER_TURN, ENEMY_TURN, WON, LOST)
    private BattleState currentState;
    //this is from UML doc seeing if battle is required or not. May not come into use.
    private boolean isScriptedBattle;

    //keep track of specific groups, check if a team is wiped out or not.
    private List<PlayerCharacter> players;
    private List<Enemy> enemies;

    public BattleManager(List<PlayerCharacter> players, List<Enemy> enemies) {
        this.players = players;
        this.enemies = enemies;
        this.combatants = new ArrayList<>();
        this.combatants.addAll(players);
        this.combatants.addAll(enemies);

        this.turnOrder = new LinkedList<>();
        this.currentState = BattleState.STARTING;
    }

    //called when the window loads (and when the battle first starts)
    //it calculates who is fastest and sets up the first turn.
    public void startBattle() {
        System.out.println("Battle Start!");
        calculateTurnOrder();
        nextTurn();
    }

    //sorts all fighters with highest speed first
    //This fills the turnOrder queue
    private void calculateTurnOrder() {
        turnOrder.clear();
        //sort by speed.
        combatants.sort((c1, c2) -> c2.getStats().getSpeed() - c1.getStats().getSpeed());
        turnOrder.addAll(combatants);
    }

    /*
    THE CORE LOOP
    This method advances the game to the next state
    it also skips dead characters, does poison damage, and AI turns.
    HEADS UP FOR UI:
    If it's a PLAYER turn, this method STOPS and waits for an input!
    If it's an ENEMY turn, this method runs the AI and loops IMMEDIATELY
     */
    public void nextTurn() {
        //1. Check win/loss conditions
        if (checkBattleOver()) return;

        //2. Manage Queue: If empty. Recalculate.
        if (turnOrder.isEmpty()) {
            calculateTurnOrder();
        }

        //3. bring up the next fighter
        Character currentCharacter = turnOrder.poll();

        //4. Check if this character is dead, if it is, skip their turn
        if (!currentCharacter.isAlive()) {
            nextTurn(); //recursive call next character
            return;
        }

        //5. Apply status effects
        currentCharacter.updateStatusEffects();
        //6. If bro dies, skip to the next turn
        if(!currentCharacter.isAlive()) {
            nextTurn();
            return;
        }

        // 7. If the character is frozen, they skip their turn
        if (currentCharacter.hasStatusEffect(FrozenEffect.class)) {
            System.out.println(currentCharacter.getName() + " is frozen and skips their turn!");
            nextTurn();
            return;
            }

        //8. WHO IS ACTING?
        // PLAYER TURN
        if (currentCharacter instanceof PlayerCharacter) {
            currentState = BattleState.PLAYER_TURN;
            System.out.println(">>> It is " + currentCharacter.getName() + "'s turn!");
            currentCharacter.performTurn(this);
        } else {
            //if it's an enemy character...
            currentState = BattleState.ENEMY_TURN;
            currentCharacter.performTurn(this);
            //in UI, we may want to add a delay.
            //not heavy in importance though.
            nextTurn();
        }

    }

    //checks if the battle is over via if all players or enemies are dead
    //will update the currentState to WON or LOST.
    private boolean checkBattleOver() {
        boolean allPlayersDead = players.stream().noneMatch(Character::isAlive);
        boolean allEnemiesDead = enemies.stream().noneMatch(Character::isAlive);

        if (allPlayersDead) {
            currentState = BattleState.LOST;
            System.out.println("DEFEAT...");
            return true;
        }
        if (allEnemiesDead) {
            currentState = BattleState.WON;
            System.out.println("VICTORY!");
            return true;
        }
        return false;
    }

    //GETTERS
    public BattleState getBattleState() {return currentState;}
    public List<Enemy> getEnemies() { return enemies; }
    public List<PlayerCharacter> getPlayers() { return players; }
}
