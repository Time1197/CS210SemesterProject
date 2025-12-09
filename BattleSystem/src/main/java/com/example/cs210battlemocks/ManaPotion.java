package com.example.cs210battlemocks;

//mana potion to heal character. extends item
public class ManaPotion extends Item{
    private double recoverAmount;

    public ManaPotion() {
        super("Mana Potion", "Restores 50 MP");
        this.recoverAmount = 0.5;
    }

    @Override
    public void use(Character target) {
        target.heal(recoverAmount);
        System.out.println("Used Mana Potion on " + target.getName() + " for" + recoverAmount * 100 + " MP.");
    }

}