package com.example.cs210battlemocks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//The brains of the operation. Manages the flow of the battle.
public class BattleManager {
    private List<Character> combatants;
    private Queue<Character> turnOrder;
    private BattleState currentState;
    //this is from UML doc seeing if battle is required or not. May not come into use.
    private boolean isScriptedBattle;

    //keep track of specific groups
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

    public void startBattle() {
        System.out.println("Battle Start!");
        calculateTurnOrder();
        nextTurn();
    }

    private void calculateTurnOrder() {
        turnOrder.clear();
        //sort by speed.
        combatants.sort((c1, c2) -> c2.getStats().getSpeed() - c1.getStats().getSpeed());
        turnOrder.addAll(combatants);
    }

    public boolean hasStatusEffect(Class<? extends StatusEffect> type) {
        for (StatusEffect effect : activeEffects) {
            if (type.isInstance(effect)) {
                return true;
            }
        }
        return false;

    public void nextTurn() {
        //1. Check win/loss conditions
        if (checkBattleOver()) return;

        //2. Manage Queue: If empty. Recalculate.
        if (turnOrder.isEmpty()) {
            calculateTurnOrder();
        }

        Character currentCharacter = turnOrder.poll();

        //3. Skip any dead characters
        if (!currentCharacter.isAlive()) {
            nextTurn(); //recursive call next character
            return;
        }

        //4. status effect ticks
        currentCharacter.updateStatusEffects();
        if(!currentCharacter.isAlive()) {
            nextTurn();
            return;
        }

        // 5. If the character is frozen, they skip their turn
        if (currentCharacter.hasStatusEffect(FrozenEffect.class)) {
            System.out.println(currentCharacter.getName() + " is frozen and skips their turn!");
            nextTurn();
            return;
            }

        //6. Determine State based on who is acting
        //if it's a player character
        if (currentCharacter instanceof PlayerCharacter) {
            currentState = BattleState.PLAYER_TURN;
            System.out.println(">>> It is " + currentCharacter.getName() + "'s turn!");
            currentCharacter.performTurn(this);
        } else {
            //if it's an enemy character...
            currentState = BattleState.ENEMY_TURN;
            currentCharacter.performTurn(this);
            //in UI, we may want to add a delay.
            nextTurn();
        }

    }

    //checks if the battle is over via if all players or enemies are dead
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

    public BattleState getBattleState() {return currentState;}

    public List<PlayerCharacter> getPlayers() {
        return players;
    }
}
