package com.example.cs210battlemocks;

//mana potion to restore character's mana. extends item
public class ManaPotion extends Item{
    private int recoverAmount;

    public ManaPotion() {
        super("Mana Potion", "Restores 50 MP");
        this.recoverAmount = 50;
    }

    @Override
    public void use(Character target) {
        target.gainMana(recoverAmount);
        System.out.println("Used Mana Potion on " + target.getName() + " for " + recoverAmount + " MP.");
    }

}