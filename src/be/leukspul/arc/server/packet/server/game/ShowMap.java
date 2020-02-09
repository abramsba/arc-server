package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class ShowMap extends GameWritePacket {
    public ShowMap(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0x9d);
        writeD(1665);
        writeD(0);
    }
}
