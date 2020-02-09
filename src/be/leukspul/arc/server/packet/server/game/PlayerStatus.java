package be.leukspul.arc.server.packet.server.game;


import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class PlayerStatus extends GameWritePacket {
    public PlayerStatus(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0xF3);
        writeD(0); // Charges
        writeD(0); // Weight Penalty
        writeD(0); // Chat ban/refusal
        writeD(0); // In danger zone
        writeD(0); // Expertise penalty
        writeD(0); // Charm?
        writeD(0); // Death penalty level
    }
}
