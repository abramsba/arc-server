package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

public class TargetCancel extends GameReadPacket {
    public static int opcode = Opcodes.TARGET_CANCEL;

    public TargetCancel(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        Unselect = readH();
        return true;
    }

    @Override
    public void run() {
        getClient().controller().clear();
    }

    private int Unselect;
}
