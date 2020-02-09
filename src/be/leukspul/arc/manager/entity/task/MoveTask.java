package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.calc.Calculator;
import be.leukspul.arc.manager.Visibility;
import be.leukspul.arc.server.packet.server.game.EntityMove;
import be.leukspul.arc.server.packet.server.game.EntityStop;
import be.leukspul.arc.server.shard.Broadcast;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;
import be.leukspul.data.ecs.component.RefTree;
import be.leukspul.data.tree.quad.TreeNode;
import be.leukspul.math.Vector3;

import java.util.LinkedList;
import java.util.Queue;

public class MoveTask extends BaseTask {

    public MoveTask(Shard shard, Entity entity, Vector3 pos) {
        super(shard, entity);
        position = pos;
        path = new LinkedList<>();
        loop = true;
    }

    public void onStart() {
        if (entity.get(Avatar.class).Sitting) {
            StandTask stand = new StandTask(shard, entity);
            stand.execute();
        }
    }

    @Override
    public void execute() {
        try {
        boolean clientSees = shard.clientPresent(entity.position(), Visibility.ViewDistance);
        if (clientSees) {
            boolean canSee = shard.geoEngine().canSeeTarget(entity, position);
            if (canSee) {
                path.add(position);
            }
            else {
                path = shard.geoEngine().findPath(entity.position(), position);
                if (path == null) {
                    loop = false;
                    return;
                }
            }
        }
        else {
            // Estimate arrival time
            double distance = Math.sqrt(entity.position().dist2(position));
            double speed = Calculator.realSpeed(entity);
            double seconds = distance / speed;
            Thread.sleep((long)seconds * 1000);
            entity.position().copy(position);
            return;
        }

        for(Vector3 point : path) {
            loop = true;
            startTime = System.currentTimeMillis();
            double radians = entity.position().atan2(point);
            double xoff = Math.cos(radians);
            double yoff = Math.sin(radians);
            double dist2 = point.dist2(entity.position());
            if (shard.clientPresent(entity.position(), Visibility.ViewDistance)) {
                entity.direction().radians(radians);
                Broadcast.ToNearbyClients(shard, entity, Visibility.ViewDistance, new EntityMove(shard, entity, point));
            }
            double totalmoved = 0;

                while (loop) {
                    long now = System.currentTimeMillis();
                    double moved = (Calculator.realSpeed(entity) * (now - startTime)) / 1000.0;
                    totalmoved += moved;
                    if (Math.pow(totalmoved, 2) > dist2) {
                        entity.position().copy(point);
                        TreeNode<Entity> treeNode = entity.get(RefTree.class).treeNode;
                        shard.tree().move(treeNode, entity.position().x(), entity.position().y());
                        Broadcast.ToNearbyClients(shard, entity, Visibility.ViewDistance, new EntityMove(shard, entity, point));
                        loop = false;

                    } else {
                        entity.position().add(new Vector3(xoff * moved, yoff * moved, 0));
                        Thread.sleep(50);
                    }
                    startTime = now;
                }
            }
        }
        catch (InterruptedException e) {
            return;
        }
    }

    public void onCancel() {
        loop = false;
        TreeNode<Entity> treeNode = entity.get(RefTree.class).treeNode;
        shard.tree().move(treeNode, entity.position().x(), entity.position().y());
        Broadcast.ToNearbyClients(shard, entity, Visibility.ViewDistance, new EntityStop(shard, entity));
    }

    private Vector3 position;
    private Queue<Vector3> path;
    private long lastCheck;
    private long startTime;

}
