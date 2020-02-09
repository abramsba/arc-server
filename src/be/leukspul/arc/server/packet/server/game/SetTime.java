package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class SetTime extends GameWritePacket {
    public SetTime(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0xEC);
        writeD(700);
        writeD(6);
    }
}
