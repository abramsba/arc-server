package be.leukspul.arc.manager.admin;

import be.leukspul.arc.manager.entity.task.TraverseTask;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefShard;
import be.leukspul.data.graph.Node;
import be.leukspul.data.tree.condition.TrueCondition;

public class AdminTraverse extends AdminCommand {
    public AdminTraverse(String input) {
        super(input);
    }

    @Override
    public void execute(Entity admin) {
        int objectA = 268436004; //Integer.parseInt(Arguments.get(0));
        int objectB = 268436005; //Integer.parseInt(Arguments.get(1));

        Shard shard = admin.get(RefShard.class).Shard;
        Entity entA = shard.entity(objectA);
        Entity entB = shard.entity(objectB);

        // Get closest node to entA
        Node nearestNode = shard.gps().nearest(entA.position().x(), entA.position().y(), new TrueCondition<>()).get();
        Node targetNode = shard.gps().nearest(entB.position().x(), entB.position().y(), new TrueCondition<>()).get();
        TraverseTask task = new TraverseTask(shard, entA, nearestNode, targetNode, null);
        shard.entityManager().stop(entA);
        shard.entityManager().add(task);
    }
}
