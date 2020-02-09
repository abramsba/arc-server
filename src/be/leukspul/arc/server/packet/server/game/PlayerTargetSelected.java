package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class PlayerTargetSelected extends GameWritePacket {
    public PlayerTargetSelected(Shard s, Entity e) {
        super(s);
        Entity = e;
    }

    @Override
    protected void write() {
        writeC(0xa6);
        writeD(Entity.id());
        writeH(0); // Color
    }

    private Entity Entity;
}
