package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class MacroList extends GameWritePacket {
    public MacroList(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0xe7);
        writeD(0); // revision
        writeC(0); //unnkown
        writeC(0); // count
        writeC(0); // unknown

    }
}
