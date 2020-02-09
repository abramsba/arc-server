package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.manager.admin.AdminEntityInfo;
import be.leukspul.arc.manager.entity.task.BaseTask;
import be.leukspul.arc.manager.entity.task.SitTask;
import be.leukspul.arc.manager.entity.task.StandTask;
import be.leukspul.arc.manager.entity.task.WanderTask;
import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.graph.Node;
import be.leukspul.data.tree.condition.TrueCondition;

public class Bypass extends GameReadPacket {
    public static int opcode = Opcodes.BYPASS_TO_SERVER;

    public Bypass(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        command = readS();
        return true;
    }

    @Override
    public void run() {
        String [] parts = command.split("_");
        switch(parts[0]) {
            case "admin":
                adminBypass(parts);
                break;
            default:
                normalBypass(parts);
        }
    }

    private void adminBypass(String [] parts) {
        AdminEntityInfo info = new AdminEntityInfo("");
        Entity entity = shard().entity(Integer.parseInt(parts[2]));
        BaseTask task = null;
        switch (parts[1]) {
            case "refresh":
                info.execute(getClient().entity());
                break;
            case "cancel":
                int index = Integer.parseInt(parts[3]);
                task = shard().entityManager().entityTasks(entity).get(index);
                shard().entityManager().cancel(entity, task);
                info.execute(getClient().entity());
                break;
            case "run":
                String taskName = parts[3];
                switch (taskName) {
                    case " Wander":
                        Node graphNode = shard().gps().nearest(entity.position().x(), entity.position().y(), new TrueCondition<>()).get();
                        WanderTask wander = new WanderTask(shard(), entity, graphNode);
                        shard().entityManager().add(wander);
                        break;
                    case " Sit":
                        SitTask sit = new SitTask(shard(), entity);
                        shard().entityManager().add(sit);
                        break;
                    case " Stand":
                        StandTask stand = new StandTask(shard(), entity);
                        shard().entityManager().add(stand);
                        break;
                }
                info.execute(getClient().entity());
                break;
            case "delete":
                shard().deleteEntity(entity);
                break;
            case "stop":
                shard().entityManager().stop(entity);
                info.execute(getClient().entity());
                break;
        }
    }

    private void normalBypass(String [] parts) {

    }

    private String command;
}
