package be.leukspul.arc.server.shard;

import be.leukspul.arc.manager.entity.EntityType;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;
import be.leukspul.data.ecs.component.Door;
import be.leukspul.data.ecs.component.Item;

public class Identify {
    public static EntityType type(Entity entity) {
        if (entity.has(Avatar.class)) {
            return EntityType.Avatar;
        }
        else if (entity.has(Door.class)) {
            return EntityType.Door;
        }
        else if (entity.has(Item.class)) {
            return EntityType.Item;
        }
        return null;
    }
}
