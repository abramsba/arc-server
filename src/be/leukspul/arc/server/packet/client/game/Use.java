package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

public class Use extends GameReadPacket {
    public static int opcode = Opcodes.ACTION_USE;

    public Use(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        ActionId = readD();
        Ctrl = (readD() == 1);
        Shift = (readC() == 1);
        return true;
    }

    @Override
    public void run() {
        getClient().controller().used(ActionId, Ctrl, Shift);
    }

    private int ActionId;
    private boolean Ctrl;
    private boolean Shift;

}
