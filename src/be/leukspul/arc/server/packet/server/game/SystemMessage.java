package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class SystemMessage extends GameWritePacket {
    public SystemMessage(Shard s, String message) {
        super(s);
        Message = message;
    }

    @Override
    protected void write() {
        writeC(0x64);
        writeD(1987);
        writeD(1);
        writeD(0);
        writeS(Message);
    }

    String Message;
}
