package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class QuestList extends GameWritePacket {
    public QuestList(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0x80);
        writeH(0);
    }


}
