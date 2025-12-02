package com.example.cs210battlemocks;

//simple poison effect. ticks across time.
public class PoisonEffect extends StatusEffect{
    private int damagePerTurn;

    public PoisonEffect(int duration, int damagePerTurn) {
        super("Poison", duration);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void onApply(Character target) {
        System.out.println(target.getName() + " was poisoned!");
    }

    @Override
    public void onTurnTick(Character target) {
        System.out.println(target.getName() + " takes " + damagePerTurn + " poison damage");
        target.takeDamage(damagePerTurn);
    }

    @Override
    public void onRemove(Character target) {
        System.out.println(target.getName() + " is no longer poisoned.");
    }

}
