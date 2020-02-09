package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class TargetUnselected extends GameWritePacket {
    public TargetUnselected(Shard s, Entity e) {
        super(s);
        entity = e;
    }

    @Override
    protected void write() {
        writeC(0x2a);
        writeD(entity.id());
        writeD((int)entity.position().x());
        writeD((int)entity.position().y());
        writeD((int)entity.position().z());
    }

    private Entity entity;
}
