package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.LeaveWorld;
import be.leukspul.arc.server.shard.Shard;

public class Logout extends GameReadPacket {
    public static int opcode = Opcodes.LOGOUT;

    public Logout(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        return true;
    }

    @Override
    public void run() {
        getClient().close(new LeaveWorld(shard()));
    }
}
