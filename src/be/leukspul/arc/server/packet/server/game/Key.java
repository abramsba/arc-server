package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class Key extends GameWritePacket {
    public Key(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0x00);
        writeC(0x01);
        writeB(getClient().key());
        writeD(0x01);
        writeD(0x01);
    }
}
