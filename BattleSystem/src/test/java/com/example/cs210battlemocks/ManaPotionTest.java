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
        // reduce MP so the potion has an effect
        hero.gainMana(-30);  // 100 ==> 70 MP
        int initialMP = hero.getCurrentMP();

        // use potion
        manaPotion.use(hero);

        // expected MP is initial + 50, capped at maxMP
        int expectedMP = Math.min(initialMP + 50, hero.getMaxMP());

        assertEquals(expectedMP, hero.getCurrentMP());
    }
}