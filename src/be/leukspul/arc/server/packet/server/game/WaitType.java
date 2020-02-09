package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;

public class WaitType extends GameWritePacket {

    public WaitType(Shard s, Entity e) {
        super(s);
        E = e;
    }

    @Override
    protected void write() {
        Avatar avatar = E.get(Avatar.class);
        writeC(0x2f);
        writeD(E.id());
        writeD(avatar.Sitting ? 0 : 1);
        writeD((int)E.position().x());
        writeD((int)E.position().y());
        writeD((int)E.position().z());
    }

    private Entity E;
}
