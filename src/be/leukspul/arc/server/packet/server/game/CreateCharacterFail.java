package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class CreateCharacterFail extends GameWritePacket {
    public CreateCharacterFail(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0x1a);
        writeD(0x05);
    }
}
