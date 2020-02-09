package be.leukspul.arc.manager.entity;

import be.leukspul.arc.library.model.DoorTemplate;
import be.leukspul.arc.library.model.NpcTemplate;
import be.leukspul.arc.library.model.PcTemplate;
import be.leukspul.arc.manager.entity.task.BaseTask;
import be.leukspul.arc.manager.inventory.EntityInventory;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.*;
import be.leukspul.math.Vector3;

import java.util.List;
import java.util.function.Consumer;

public class EntityFactory {

    public static Consumer<List<BaseTask>> getTaskFunction(Shard shard, Entity entity) {
        return baseTasks -> baseTasks.addAll(shard.entityManager().entityTasks(entity));
    }

    public static Entity createPlayer(Shard shard, String name, PcTemplate template, Vector3 position, EntityOptions options) {
        Entity player = new Entity(shard.nextID());
        EntityInventory inventory = new EntityInventory(shard, player);
        Avatar avatar = new Avatar(template, options.Gender, options.Face, options.Hair, options.Color);
        Attributes attributes = new Attributes(1, 1, 1, 1, 1, 1);
        Vitals vitals = new Vitals(100, 100);
        RefInventory refInventory = new RefInventory(inventory);
        RefShard refShard = new RefShard(shard);
        RefTask refTask = new RefTask();
        refTask.List = getTaskFunction(shard, player);
        player.position().copy(position);
        avatar.Name = name;
        avatar.BonusSpeed = 500;
        player.add(avatar);
        player.add(attributes);
        player.add(vitals);
        player.add(refInventory);
        player.add(refShard);
        player.add(refTask);
        player.add(new Skills());
        return player;
    }

    public static Entity createDoor(Shard shard, DoorTemplate template) {
        Entity door = new Entity(shard.nextID());
        Door doorComp = new Door(template);
        door.position().set(template.x, template.y, template.z);
        door.add(doorComp);
        return door;
    }

    public static Entity createNpc(Shard shard, String name, NpcTemplate template, Vector3 position, EntityOptions options) {
        Entity npc = new Entity(shard.nextID());
        EntityInventory inventory = new EntityInventory(shard, npc);
        Avatar avatar = new Avatar(template);
        Attributes attributes = new Attributes(1, 1, 1, 1, 1, 1);
        Vitals vitals = new Vitals(100, 100);
        RefInventory refInventory = new RefInventory(inventory);
        RefShard refShard = new RefShard(shard);
        RefTask refTask = new RefTask();
        refTask.List = getTaskFunction(shard, npc);
        npc.position().copy(position);
        avatar.Name = name;
        avatar.Gender = options.Gender;
        npc.add(avatar);
        npc.add(attributes);
        npc.add(vitals);
        npc.add(refInventory);
        npc.add(refShard);
        npc.add(refTask);
        npc.add(new Skills());

        return npc;
    }

}
