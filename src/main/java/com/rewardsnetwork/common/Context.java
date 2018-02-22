package com.rewardsnetwork.common;

import java.util.ArrayList;
import java.util.List;

public class Context {
    public enum Temperature {
        HOT,
        COLD;

        public static Temperature getByName(String name) {

            Temperature temperature;
            
            try {
                temperature = Temperature.valueOf(name.toUpperCase());

            } catch (IllegalArgumentException e) {
                temperature = null;
            }

            return temperature;
        }
    }

    private Temperature temperature;
    private List<Item> itemsOn;
    private boolean done;

    public Context(Temperature temperature) {
        this.temperature = temperature;
        
        this.itemsOn = new ArrayList<>();
        itemsOn.add(Item.PJ);  // You start in the house with your PJ's on
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public List<Item> getItemsOn() {
        return itemsOn;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone() {
        this.done = true;
    }
}
