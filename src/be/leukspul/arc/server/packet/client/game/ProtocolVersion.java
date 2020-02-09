package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.Key;
import be.leukspul.arc.server.shard.Shard;

public class ProtocolVersion extends GameReadPacket {
    public static int opcode = Opcodes.PROTOCOL_VERSION;

    public ProtocolVersion(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        int version = readD();
        return true;
    }

    public void run() {
        getClient().send(new Key(shard()));
    }
}
