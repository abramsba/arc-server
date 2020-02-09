package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.shard.Shard;

public class ManorList extends GameReadPacket {
    public static int opcode = 8;

    public ManorList(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        return true;
    }

    @Override
    public void run() {
        getClient().send(new be.leukspul.arc.server.packet.server.game.ManorList(shard()));
    }
}
