package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.math.Vector3;

public class EntityMove extends GameWritePacket {
    public EntityMove(Shard s, Entity e, int x, int y, int z) {
        super(s);
        E = e;
        X = x;
        Y = y;
        Z = z;
    }

    public EntityMove(Shard s, Entity e, Vector3 pos) {
        super(s);
        E = e;
        X = (int)pos.x();
        Y = (int)pos.y();
        Z = (int)pos.z();
    }

    @Override
    protected void write() {
        writeC(0x01);
        writeD(E.id());
        writeD(X);
        writeD(Y);
        writeD(Z);
        writeD((int)E.position().x());
        writeD((int)E.position().y());
        writeD((int)E.position().z());
    }

    private Entity E;
    private int X, Y, Z;


}
