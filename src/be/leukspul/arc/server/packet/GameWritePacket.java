package be.leukspul.arc.server.packet;

import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import net.sf.l2j.commons.mmocore.SendablePacket;

public abstract class GameWritePacket extends SendablePacket<GameClient> {
    public GameWritePacket(Shard s) {
        shard = s;
    }
    public Shard shard() {
        return shard;
    }
    protected void writePosition(Entity entity) {
        writeD((int)entity.position().x());
        writeD((int)entity.position().y());
        writeD((int)entity.position().z());
    }
    protected void writeDirection(Entity entity) {
        writeD((int)entity.direction().heading());
    }
    private Shard shard;
}
