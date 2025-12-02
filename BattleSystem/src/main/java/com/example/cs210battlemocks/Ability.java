package com.example.cs210battlemocks;

//The ability class is for both allies and enemies. Add them to a character to give them the ability!
// - Keenan
public class Ability {
    private String name;
    private String description;
    private int manaCost;
    private int power;
    private StatusEffect effectToApply; //can be null

    public Ability(String name, String description, int manaCost, int power, StatusEffect effectToApply) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.power = power;
        this.effectToApply = effectToApply;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void execute(Character caster, Character target) {
        //1. pay mana
        caster.gainMana(-manaCost);

        //2. Calculate damage, ability type based on power.
        if (this.power > 0) {
            //an attack
            //formula: power + magicStat - EnemyDefense
            //Math.max is there to ensure that at least 1 dmg is always there
            int damage = Math.max(1, this.power + caster.getStats().getMagicAtk() - target.getStats().getDefense());
            target.takeDamage(damage);
            System.out.println(caster.getName() + " attacked " + target.getName() + " for " + damage + " dmg.");
        } else if (this.power < 0) {
            //healing logic
            //formula: absolute value of power + magic
            int healAmount = Math.abs(this.power) + caster.getStats().getMagicAtk();
            target.heal(healAmount);
            System.out.println(caster.getName() + " healed " + target.getName() + " for " + healAmount + " HP.");
        } else {
            //utility logic (power == 0)
            System.out.println(caster.getName() + " used " + this.name + " on " + target.getName());
        }

        //3. Apply effect if it exists (works will all 3 types)
        if (effectToApply != null) {
            target.addStatusEffect(effectToApply);
        }
    }

}
