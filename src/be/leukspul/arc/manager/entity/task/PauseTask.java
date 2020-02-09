package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class PauseTask extends BaseTask {
    public PauseTask(Shard shard, Entity entity, int ms) {
        super(shard, entity);
        this.ms = ms;
    }

    @Override
    public void execute() {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            return;
        }
    }

    public int ms;
}
