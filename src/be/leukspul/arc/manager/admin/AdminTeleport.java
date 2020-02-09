package be.leukspul.arc.manager.admin;

import be.leukspul.arc.manager.entity.task.TeleportTask;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefShard;
import be.leukspul.math.Vector3;

public class AdminTeleport extends AdminCommand {
    public AdminTeleport(String input) {
        super(input);
    }

    @Override
    public void execute(Entity admin) {
        Shard shard = admin.get(RefShard.class).Shard;
        int x = IntArg(0);
        int y = IntArg(1);
        int z = IntArg(2);
        TeleportTask teleport = new TeleportTask(shard, admin, new Vector3(x, y, z));

        shard.entityManager().stop(admin);
        shard.entityManager().add(teleport);
    }


}
