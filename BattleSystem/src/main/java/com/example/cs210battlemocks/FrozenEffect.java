package com.example.cs210battlemocks;

// status effect that prevents the target from acting for a number of turns
public class FrozenEffect extends StatusEffect {

    public FrozenEffect(int duration) {
        super("Frozen", duration);
    }

    @Override
    public void onApply(Character target) {
        System.out.println(target.getName() + " was frozen solid!");
    }

    @Override
    public void onTurnTick(Character target) {
        // no damage, just a message (the actual “skip turn” is handled in BattleManager)
        System.out.println(target.getName() + " is frozen and cannot act this turn.");
    }

    @Override
    public void onRemove(Character target) {
        System.out.println(target.getName() + " is no longer frozen.");
    }
}