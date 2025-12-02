package com.example.cs210battlemocks;

//a simple class that will control how an AI acts.
public class SimpleAI implements AIBrain {

    @Override
    public void decideAction(Enemy self, BattleState state) {
        //basic logic right now. Attack.
        System.out.println("[AI] " + self.getName() + " decides to ATTACK!");

        //to actually deal damage, this will have to be wired up later. for now, we must
        //just check if all is operational.
    }

}
