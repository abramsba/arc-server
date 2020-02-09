package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.server.packet.server.game.Teleport;
import be.leukspul.arc.server.shard.Broadcast;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefTree;
import be.leukspul.data.tree.quad.TreeNode;
import be.leukspul.math.Vector3;

public class TeleportTask extends BaseTask {
    public TeleportTask(Shard shard, Entity entity, Vector3 pos) {
        super(shard, entity);
        position = pos;
    }

    @Override
    public void execute() {
        Teleport packet = new Teleport(shard, entity, position);
        Broadcast.ToNearbyClients(shard, entity, 20000, packet);
        entity.position().copy(position);
        TreeNode<Entity> node = entity.get(RefTree.class).treeNode;
        shard.tree().move(node, position.x(), position.y());
    }

    private Vector3 position;
}
