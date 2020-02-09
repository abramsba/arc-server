package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class LeaveWorld extends GameWritePacket {
    public LeaveWorld(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0x7e);
    }
}
