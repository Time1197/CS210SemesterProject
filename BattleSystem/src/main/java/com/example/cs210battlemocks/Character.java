package com.example.cs210battlemocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Character {
    protected String name;
    protected int currentHP;
    protected int maxHP;
    protected int currentMP;
    protected int maxMP;
    protected Stats stats;
    protected List<Ability> abilities;
    protected List<StatusEffect> activeEffects;

    public Character(String name, int maxHP, int maxMP, Stats stats) {
        this.name = name;
        this.currentHP = maxHP;
        this.maxHP = maxHP;
        this.currentMP = maxMP;
        this.maxMP = maxMP;
        this.stats = stats;
        this.abilities = new ArrayList<>();
        this.activeEffects = new ArrayList<>();
    }

    //Abstract: children must define how they will act
    //requires the battle manager class.
    public abstract void preformTurn(BattleManager manager);

    //--- COMBAT ---
    public void takeDamage(int amount) {
        this.currentHP -= amount;
        if (this.currentHP < 0) this.currentHP = 0;
        System.out.println(this.name + " took " + amount + " damage. HP remaining: " + currentHP + "/" + maxHP);
    }

    public void heal(int amount) {
        this.currentHP += amount;
        if (this.currentHP > maxHP) this.currentHP = maxHP;
        System.out.println(this.name + " healed " + amount + " damage. HP: " + currentHP + "/" + maxHP);
    }

    public void fullHeal() {
        this.currentHP = maxHP;
    }

    public void gainMana(int amount) {
        this.currentMP += amount;
        if (this.currentMP > maxMP) this.currentMP = maxMP;
        if (this.currentMP < 0) this.currentMP = 0;
    }

    public void addStatusEffect(StatusEffect effect) {
        this.activeEffects.add(effect);
        effect.onApply(this);
    }

    public void updateStatusEffects() {
        Iterator<StatusEffect> iterator = activeEffects.iterator();
        while(iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            effect.onTurnTick(this);
            effect.decrementDuration();

            if(effect.getDuration() <= 0) {
                effect.onRemove(this);
                iterator.remove();
            }
        }
    }

    public boolean isAlive() {
        return this.currentHP > 0;
    }

    public void learnAbility(Ability ability) {
        this.abilities.add(ability);
    }

    //--- GETTERS ---
    public String getName() {
        return name;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentMP() {
        return currentMP;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public Stats getStats() {
        return stats;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }
}
