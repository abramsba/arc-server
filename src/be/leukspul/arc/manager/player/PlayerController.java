package be.leukspul.arc.manager.player;

import be.leukspul.arc.manager.Visibility;
import be.leukspul.arc.manager.entity.task.BaseTask;
import be.leukspul.arc.manager.entity.task.MoveTask;
import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.packet.server.game.PlayerTargetSelected;
import be.leukspul.arc.server.packet.server.game.Static;
import be.leukspul.arc.server.packet.server.game.TargetSelected;
import be.leukspul.arc.server.packet.server.game.TargetUnselected;
import be.leukspul.arc.server.shard.Broadcast;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefClient;
import be.leukspul.data.ecs.component.RefShard;
import be.leukspul.data.ecs.component.RefTarget;
import be.leukspul.math.Vector3;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class PlayerController {

    public PlayerController(Entity e) {
        entity = e;
        shard = e.get(RefShard.class).Shard;
        client = e.get(RefClient.class).Client;
        exec = Executors.newSingleThreadScheduledExecutor();
    }

    public void move (Vector3 origin, Vector3 target) {
        //EntityMove packet = new EntityMove(shard, entity, target);
        MoveTask task = new MoveTask(shard, entity, target);
        shard.entityManager().stop(entity);
        shard.entityManager().add(task);

        /*
        if (task != null) {
            task.stop();
            future.cancel(true);
            task = null;
            future = null;
        }
        entity.position().copy(origin);
        if (task == null) {
            task = new MoveTask(shard, entity, target);
            Runnable thread = () -> {
                task.execute();
            };
            future = exec.schedule(thread, 0, TimeUnit.MILLISECONDS);
        }
        */
    }

    public void clicked (int id, int action) {
        if (id == entity.id()) {
            set(entity);
            return;
        }

        Entity clicked = client.visibility().find(id);
        if (clicked != null) {
            set(clicked);
        }
        System.out.println(id + ", " + action);
        client.send(new Static(shard));
    }

    public void used (int action, boolean ctrl, boolean shift) {
        client.send(new Static(shard));

    }

    public boolean has() {
        return Target != null;
    }

    public Entity get() {
        return Target;
    }

    public void clear() {
        GameClient client = entity.get(RefClient.class).Client;
        Shard shard = entity.get(RefShard.class).Shard;
        entity.remove(RefTarget.class);
        Target = null;
        TargetUnselected packet = new TargetUnselected(shard, entity);
        Broadcast.ToNearbyClients(shard, entity, Visibility.ViewDistance, packet);
        client.send(new Static(shard));
    }

    public void set(Entity target) {
        GameClient client = entity.get(RefClient.class).Client;
        Shard shard = entity.get(RefShard.class).Shard;
        client.send(new PlayerTargetSelected(shard, target));
        Target = target;
        if (!entity.has(RefTarget.class)) {
            entity.add(new RefTarget(entity));

        }
        entity.get(RefTarget.class).Entity = Target;
        Broadcast.ToNearbyClients(shard, entity, Visibility.ViewDistance, new TargetSelected(shard, entity, Target));
    }

    // TODO Actions
    private Entity Target;
    private Entity entity;
    private Shard shard;
    private GameClient client;
    private ScheduledExecutorService exec;
    private BaseTask task;
    private Future future;
    private boolean tasking;
}
