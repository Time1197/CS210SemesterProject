package com.example.cs210battlemocks;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    //Maps an Item to the quantity  owed.
    private Map<Item, Integer> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void addItem(Item item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(Item item, int quantity) {
        if (!items.containsKey(item)) return;

        int currentQty = items.get(item);
        if (currentQty <= quantity) {
            items.remove(item);
        } else {
            items.put(item, currentQty - quantity);
        }
    }

    public boolean hasItem(Item item) {
        return items.containsKey(item);
    }

    //probably will be helpful for future ui usage.
    public Map<Item, Integer> getItems() {
        return items;
    }

}
