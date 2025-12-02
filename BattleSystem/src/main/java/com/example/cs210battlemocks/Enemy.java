package com.example.cs210battlemocks;

//AI enemy. should have an automatic turn
public class Enemy extends Character {
    private AIBrain aiBrain;
    //loot table can be added here later if need be.

    public Enemy(String name, int maxHP, int maxMP, Stats stats, AIBrain aiBrain) {
        super(name, maxHP, maxMP, stats);
        this.aiBrain = aiBrain;
    }

    @Override
    public void performTurn(BattleManager manager) {
        System.out.println(this.name + " is thinking...");
        //AI decides
        aiBrain.decideAction(this, manager);
    }

}
