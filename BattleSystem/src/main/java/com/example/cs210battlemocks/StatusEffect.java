package com.example.cs210battlemocks;

// pretty self-explanatory. Status effects are applied to allies or enemies which incur temporary effects
// abstract because status effects can be pretty complex sometimes. Also abstract just in case if you don't
// want to implement all the methods.
// - Keenan
public abstract class StatusEffect {
    protected String name;
    protected int duration;

    public StatusEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void decrementDuration() {
        if (duration > 0) {
            duration--;
        }
    }

    public abstract void onApply(Character target);
    public abstract void onTurnTick(Character target);
    public abstract void onRemove(Character target);
}
