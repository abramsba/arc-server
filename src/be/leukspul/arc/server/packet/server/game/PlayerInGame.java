package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class PlayerInGame extends GameWritePacket {

    public PlayerInGame(Shard shard, String name) {
        super(shard);
        this.name = name;
    }

    @Override
    protected void write() {
        writeC(0x02);
        writeC(1);
        writeS(name);
    }

    private String name;
}
