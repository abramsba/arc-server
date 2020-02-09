package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class ValidateLocation extends GameWritePacket {
    public ValidateLocation(Shard s, Entity e) {
        super(s);
        E = e;
    }

    @Override
    protected void write() {
        writeC(0x61);
        writeD(E.id());
        writePosition(E);
        writeDirection(E);
    }

    private Entity E;
}
