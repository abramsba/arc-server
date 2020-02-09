package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

public class Action extends GameReadPacket {
    public static int opcode = Opcodes.ACTION;

    public Action(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        ObjectId = readD();
        X = readD();
        Y = readD();
        Z = readD();
        ActionId = readC();
        return true;
    }

    @Override
    public void run() {
        getClient().controller().clicked(ObjectId, ActionId);
    }

    private int ObjectId;
    private int X;
    private int Y;
    private int Z;
    private int ActionId;
}
