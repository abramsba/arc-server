package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

public class SelectedCharacter extends GameReadPacket {
    public static int opcode = Opcodes.SELECT_CHARACTER;

    public SelectedCharacter(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        slot = readD();
        readH();
        readD();
        readD();
        return true;
    }

    @Override
    public void run() {
        shard().playerEntered(getClient(), slot);
    }

    private int slot;
}
