package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.SendSkillList;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class GetSkillList extends GameReadPacket {
    public static int opcode = Opcodes.SKILL_LIST;

    public GetSkillList(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        return true;
    }

    @Override
    public void run() {
        Entity e = getClient().entity();
        getClient().send(new SendSkillList(shard(), e));
    }

}
