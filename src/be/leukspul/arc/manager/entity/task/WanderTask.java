package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.graph.Node;
import be.leukspul.math.Vector3;

import java.util.Random;

public class WanderTask extends BaseTask {

    public static Random random = new Random();

    public WanderTask(Shard shard, Entity entity, Node node) {
        super(shard, entity);
        this.node = node;
        this.prev = null;
    }

    public WanderTask(Shard shard, Entity entity, Node node, Node prev) {
        super(shard, entity);
        this.node = node;
        this.prev = prev;
    }

    @Override
    public void execute() {
        double rads = random.nextDouble() * (2 / Math.PI);
        double dist = random.nextDouble() * node.radius;
        double x = node.x + (Math.cos(rads) * dist);
        double y = node.y + (Math.sin(rads) * dist);
        MoveTask moveTask = new MoveTask(shard, entity, new Vector3(x, y, node.z));
        if (node.links() == 1) {
            prev = node;
            node = node.getLink(0);
        } else {
            boolean stop = false;
            while (!stop) {
                Node next = node.getLink(random.nextInt(node.links()));
                if (next == prev) {
                    continue;
                }
                prev = node;
                node = next;
                stop = true;
            }
        }
        if (loop) {
            shard.entityManager().add(moveTask);
            if (random.nextBoolean()) {
                shard.entityManager().add(new PauseTask(shard, entity, random.nextInt(5000)));
            }
            shard.entityManager().add(new WanderTask(shard, entity, node, prev));
        }
    }

    private Node node;
    private Node prev;
}
