package com.example.cs210battlemocks;

//this item abstract class is a guide for items to add into the video game. - Keenan
public abstract class Item {
    protected String name;
    protected String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    //abstract method: use. when the item is selected, you can use it.
    //primary created with potions in mind.
    public abstract void use(Character target);

}
