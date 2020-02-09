package be.leukspul.arc.manager.item;

import be.leukspul.data.ecs.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemManager {

    public ItemManager() {
        items = new ConcurrentHashMap<>();
    }



    private Map<Integer, Entity> items;


}
