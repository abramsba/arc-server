package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;

public class MoveType extends GameWritePacket {
    public MoveType(Shard s, Entity e) {
        super(s);
        E = e;
    }

    @Override
    protected void write() {
        Avatar avatar = E.get(Avatar.class);
        writeC(0x2e);
        writeD(E.id());
        writeD(avatar.Walking ? 0 : 1);
        writeD(0);
    }

    private Entity E;
}
