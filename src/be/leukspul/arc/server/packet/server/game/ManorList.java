package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class ManorList extends GameWritePacket {
    public ManorList(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0xfe);
        writeH(0x1b);
        writeD(Data.length);
        for(int i = 0; i < Data.length; i++) {
            writeD(i+1);
            writeS(Data[i]);
        }
    }

    private static final String[] Data = {
            "gludio",
            "dion",
            "giran",
            "oren",
            "aden",
            "innadril",
            "goddard",
            "rune",
            "schuttgart"
    };
}
