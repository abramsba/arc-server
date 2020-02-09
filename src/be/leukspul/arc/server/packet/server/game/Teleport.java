package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.math.Vector3;

public class Teleport extends GameWritePacket {

    public Teleport(Shard s, Entity entity, int x, int y, int z) {
        super(s);
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Teleport(Shard s, Entity entity, Vector3 pos) {
        super(s);
        this.entity = entity;
        this.x = (int)pos.x();
        this.y = (int)pos.y();
        this.z = (int)pos.z();
    }

    @Override
    protected void write() {
        writeC(0x28);
        writeD(entity.id());
        writeD(x);
        writeD(y);
        writeD(z);
    }

    private int x, y, z;
    private Entity entity;
}
