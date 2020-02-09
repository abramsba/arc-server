package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.ShowMap;
import be.leukspul.arc.server.shard.Shard;

public class ViewMap extends GameReadPacket {
    public static int opcode = Opcodes.SHOW_MINIMAP;

    public ViewMap(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        return true;
    }

    @Override
    public void run() {
        getClient().send(new ShowMap(shard()));
    }
}
