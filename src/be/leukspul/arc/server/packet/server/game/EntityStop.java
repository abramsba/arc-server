package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class EntityStop extends GameWritePacket {
    public EntityStop(Shard s, Entity entity) {
        super(s);
        this.entity = entity;
    }

    @Override
    protected void write() {
        writeC(0x47);
        writeD(entity.id());
        writePosition(entity);
        writeDirection(entity);
    }

    private Entity entity;
}
