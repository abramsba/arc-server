package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;

public abstract class BaseTask {
    public BaseTask(Shard shard, Entity entity) {
        this.entity = entity;
        this.shard = shard;
        this.loop = true;
    }
    public abstract void execute();
    public Entity entity() {
        return entity;
    }
    public Shard shard() {
        return shard;
    }
    public void stop() {
        loop = false;
    }
    public boolean looping() { return loop; }
    protected Entity entity;
    protected Shard shard;
    protected Avatar avatar;
    protected boolean loop;

    public BaseTask tail() {
        BaseTask current = this;
        boolean loop = true;
        while (loop) {
            if (current.next == null) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void onStart() {}
    public void onCancel() {}

    public BaseTask next;
    public boolean stop;
}
