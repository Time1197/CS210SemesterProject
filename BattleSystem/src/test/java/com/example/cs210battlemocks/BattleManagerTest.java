package com.example.cs210battlemocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

//test case: when a playerCharacter attacks an enemy with an ability, the enemy will take the
//specific amount of damage and return if the damage and math logic was correct or not.
//this JUnit test file is authored by Keenan DePaz.

class BattleManagerTest {

    private PlayerCharacter hero;
    private Enemy slime;
    private BattleManager manager;

    //this runs before EVERY single @Test method to reset the state
    @BeforeEach
    void setUp() {
        //set up our hero and our enemy
        Stats heroStats = new Stats(15, 5, 10, 5); // Spd 10
        Stats slimeStats = new Stats(10, 2, 5, 0); // Spd 5

        hero = new PlayerCharacter("Test Hero", 100, 50, heroStats);
        //we use SimpleAI since we know it works
        slime = new Enemy("Test Slime", 50, 0, slimeStats, new SimpleAI());

        //add them to the list to prepare for battle
        List<PlayerCharacter> players = new ArrayList<>();
        players.add(hero);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(slime);

        //add them to the battle Manager to prepare for battle
        manager = new BattleManager(players, enemies);
    }

    @Test
    void testDamageCalculation() {
        //setup a fireball: 20 Power
        //dmg = Power(20) + Mag(5) - Def(2) = 23
        Ability fireball = new Ability("Fireball", "Fire", 10, 20, null);

        int initialHP = slime.getCurrentHP();
        //have the hero shoot the fireball at the slime
        fireball.execute(hero, slime);

        //check if the damage equals.
        assertEquals(initialHP - 23, slime.getCurrentHP(),
                "Damage math should be exact (20+5-2=23).");
    }
}