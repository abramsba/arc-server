package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class DeleteEntity extends GameWritePacket {
    public DeleteEntity(Shard s, Entity e) {
        super(s);
        E = e;
    }

    @Override
    protected void write() {
        writeC(0x12);
        writeD(E.id());
        writeD(1);
    }

    private Entity E;
}
