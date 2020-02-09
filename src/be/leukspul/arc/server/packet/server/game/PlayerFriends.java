package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class PlayerFriends extends GameWritePacket {

    public PlayerFriends(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        // You have none
        writeC(0xfa);
        writeD(0);
    }
}
