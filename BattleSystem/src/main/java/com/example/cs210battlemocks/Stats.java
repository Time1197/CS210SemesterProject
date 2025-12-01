package com.example.cs210battlemocks;

//stats for not just the playable characters, but for all characters.
//stats are intended to be implemented into the character abstract class, playerCharacter, Enemy
//- Keenan
public class Stats {
    private int attack;
    private int defense;
    private int speed;
    private int magicAtk;

    public Stats(int attack, int defense, int speed, int magicAtk) {
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.magicAtk = magicAtk;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMagicAtk() {
        return magicAtk;
    }

    public int getAttack() {
        return attack;
    }


}
