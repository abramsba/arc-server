package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class FollowTask extends BaseTask {

    public FollowTask(Shard shard, Entity entity) {
        super(shard, entity);
    }

    @Override
    public void execute() {

    }


}
