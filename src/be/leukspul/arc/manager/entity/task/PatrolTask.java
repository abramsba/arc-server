package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class PatrolTask extends BaseTask {
    public PatrolTask(Shard shard, Entity entity) {
        super(shard, entity);
    }

    @Override
    public void execute() {

    }
}
