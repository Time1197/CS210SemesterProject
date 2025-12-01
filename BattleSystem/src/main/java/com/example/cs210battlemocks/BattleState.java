package com.example.cs210battlemocks;

//An enum is a type of class that makes new states for the program. It's like a custom boolean
//where one can select the states. Here, we are creating custom states for our battle system. - Keenan
public enum BattleState {
    STARTING,
    PLAYER_TURN,
    ENEMY_TURN,
    WON,
    LOST
}
