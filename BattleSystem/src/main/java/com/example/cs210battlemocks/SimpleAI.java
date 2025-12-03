package com.example.cs210battlemocks;

import java.util.List;

//a simple class that will control how an AI acts.
public class SimpleAI implements AIBrain {

    @Override
    public void decideAction(Enemy self, BattleManager manager) {
        //1. Pick a target (first living player)
        List<PlayerCharacter> players = manager.getPlayers();
        PlayerCharacter target = null;

        for (PlayerCharacter p : players) {
            if (p.isAlive()) {
                target = p;
                break;
            }
        }

        if (target != null) {
            System.out.println("[AI] " + self.getName() + " decides to ATTACK " + target.getName() + "!");
            //enemy attacks using a basic attack
            //note: this is NOT where the attack is supposed to go. It's just a temporary location
            Ability basicAttack = new Ability("Basic Attack", "Scratch", 0, 10, null);
            basicAttack.execute(self, target);
        } else {
            System.out.println("[AI] " + self.getName() + " has no one to attack!");
        }
        //just check if all is operational.
    }
}
