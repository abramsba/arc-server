package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.server.packet.server.game.WaitType;
import be.leukspul.arc.server.shard.Broadcast;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;

public class StandTask extends BaseTask {
    public StandTask(Shard shard, Entity entity) {
        super(shard, entity);
    }

    @Override
    public void execute() {
        if (entity.get(Avatar.class).Sitting) {
            entity.get(Avatar.class).Sitting = false;
            Broadcast.ToNearbyClients(shard, entity, 20000, new WaitType(shard, entity));
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
