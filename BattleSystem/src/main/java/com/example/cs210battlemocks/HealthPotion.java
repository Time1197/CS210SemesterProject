package com.example.cs210battlemocks;

//health potion to heal character. extends item
public class HealthPotion extends Item{
    private int healAmount;

    public HealthPotion() {
        super("Health Potion", "Restores 500 HP");
        this.healAmount = 50;
    }

    @Override
    public void use(Character target) {
        target.heal(healAmount);
        System.out.println("Used Health Potion on " + target.getName() + " for" + healAmount + " HP.");
    }

}
