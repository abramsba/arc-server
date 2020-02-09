package be.leukspul.arc.manager.admin;

import be.leukspul.arc.manager.entity.task.WanderTask;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefShard;
import be.leukspul.data.tree.condition.TrueCondition;

public class AdminWander extends AdminCommand {
    public AdminWander(String input) {
        super(input);
    }

    @Override
    public void execute(Entity admin) {
        Shard shard = admin.get(RefShard.class).Shard;
        shard.entityManager().stop(admin);
        WanderTask wander = new WanderTask(shard, admin, shard.gps().nearest(admin.position().x(), admin.position().y(), new TrueCondition<>()).get());
        shard.entityManager().add(wander);
    }
}
