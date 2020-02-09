package be.leukspul.arc.manager.inventory;

import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Item;
import be.leukspul.data.ecs.component.ItemOwner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EntityInventory {

    public EntityInventory(Shard shard, Entity entity) {
        this.entity = entity;
        this.shard = shard;
        this.items = Collections.synchronizedList(new LinkedList<>());
    }

    public boolean addItem(Entity item) {
        Item info = item.get(Item.class);

        // Make sure we can pick up the item (enough weight)

        ItemOwner owner = item.get(ItemOwner.class);

        // If the item has no owner 'world spawn', the first person who picks it up becomes owner
        if (owner == null) {
            owner = new ItemOwner(entity);
            item.add(owner);
        }

        // If stackable try to find a similar item and add the quantity
        if (info.Template.Stackable) {
            Entity existing = getItem(item.get(Item.class).Template.Id);
            if (existing != null) {
                existing.get(Item.class).Amount += info.Amount;
            }
        }
        else {
            // Notify client item added
            items.add(item);
        }
        return true;
    }

    public boolean equipItem(Entity item) {
        Item info = item.get(Item.class);
        return false;
    }

    public Entity getItem(int itemId) {
        for (Entity item : items) {
            if (item.get(Item.class).Template.Id == itemId) {
                return item;
            }
        }
        return null;
    }

    private Entity entity;
    private Shard shard;
    private List<Entity> items;
    private Entity head;
    private Entity chest;
    private Entity legs;
    private Entity feet;
    private Entity gloves;
    private Entity mainhand;
    private Entity offhand;
    private Entity ammo;

}
