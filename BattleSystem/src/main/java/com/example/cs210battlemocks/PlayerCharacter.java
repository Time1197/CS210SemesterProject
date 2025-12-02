package com.example.cs210battlemocks;

//extends the character class. Main difference is that it's player controlled and has an inventory
public class PlayerCharacter extends Character {
    private Inventory inventory;

    public PlayerCharacter(String name, int maxHP, int maxMP, Stats stats) {
        super(name, maxHP, maxMP, stats);
        this.inventory = new Inventory();
    }

    @Override
    public void preformTurn(BattleManager manager) {
        //this will probably be in javaFX?
        //signals that the logic is ready for UI to enable buttons
        System.out.println("[Logic] Waiting for player input");
    }

    public void useItem(Item item, Character target) {
        //check if the item even exists
        if (inventory.hasItem(item)) {
            item.use(target);
            inventory.removeItem(item, 1);
        } else {
            System.out.println("You do not have this item!");
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
