package com.example.cs210battlemocks;

//health potion to heal character. extends item
public class HealthPotion extends Item{
    private double healAmount;

    public HealthPotion() {
        super("Health Potion", "Restores 50 HP");
        this.healAmount = 0.5;
    }

    @Override
    public void use(Character target) {
        target.heal(healAmount);
        System.out.println("Used Health Potion on " + target.getName() + " for" + healAmount * 100 + " HP.");
    }

}
