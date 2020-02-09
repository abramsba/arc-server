package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.PlayerInfo;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class Appearing extends GameReadPacket {
    public static int opcode = Opcodes.APPEAR_AFTER_DEATH;

    public Appearing(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        Entity player = getClient().entity();
        shard().whoThere(player);
        getClient().send(new PlayerInfo(shard(), player));
        return true;
    }

    @Override
    public void run() {

    }
}
