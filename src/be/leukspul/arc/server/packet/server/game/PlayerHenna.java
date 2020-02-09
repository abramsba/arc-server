package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class PlayerHenna extends GameWritePacket {

    public PlayerHenna(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0xe4);
        writeC(0); // INT
        writeC(0); // STR
        writeC(0); // CON
        writeC(0); // MEN
        writeC(0); // DEX
        writeC(0); // WIT

        writeD(0); // No slots
        writeD(0); // Count
    }
}
