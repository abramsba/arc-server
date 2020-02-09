package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class TargetSelected extends GameWritePacket {
    public TargetSelected(Shard s, Entity source, Entity target) {
        super(s);
        Source = source;
        Target = target;
    }

    @Override
    protected void write() {
        //Position p = Source.get(Position.class);
        writeC(0x29);
        writeD(Source.id());
        writeD(Target.id());
        writeD((int)Source.position().x());
        writeD((int)Source.position().y());
        writeD((int)Source.position().z());
    }

    private Entity Source;
    private Entity Target;
}
