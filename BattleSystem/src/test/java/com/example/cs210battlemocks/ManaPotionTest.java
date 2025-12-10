package com.example.cs210battlemocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManaPotionTest {

    private PlayerCharacter hero;
    private ManaPotion manaPotion;

    @BeforeEach
    void setup() {
        Stats heroStats = new Stats(15, 5, 10, 5);
        hero = new PlayerCharacter("Test Hero", 100, 100, heroStats);
        manaPotion = new ManaPotion();
    }

    @Test
    void testManaPotionRestoresManaAndDoesNotExceedMax() {
        // show initial mp
        System.out.println("Step 1: Initial MP = " + hero.getCurrentMP());

        // reduce MP so the potion has an effect
        hero.gainMana(-30);  // 100 ==> 70 MP
        System.out.println("Step 2: After losing 30 MP, current MP = " + hero.getCurrentMP());

        int initialMP = hero.getCurrentMP();

        // use potion
        manaPotion.use(hero); // +50, capped at max
        System.out.println("Step 3: After using Mana Potion, current MP = " + hero.getCurrentMP());

        // expected MP is initial + 50, capped at maxMP
        int expectedMP = Math.min(initialMP + 50, hero.getMaxMP());
        System.out.println("Step 4: Expected MP = " + expectedMP);

        assertEquals(expectedMP, hero.getCurrentMP());
    }
}