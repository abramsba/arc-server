package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

public class NewCharacter extends GameReadPacket {
    public static int opcode = Opcodes.NEW_CHARACTER;

    public NewCharacter(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        return true;
    }

    @Override
    public void run() {
    }
}
