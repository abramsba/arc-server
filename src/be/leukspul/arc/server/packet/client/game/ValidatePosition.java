package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

public class ValidatePosition extends GameReadPacket {
    public static int opcode = Opcodes.VALIDATE_POSITION;

    public ValidatePosition(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        X = readD();
        Y = readD();
        Z = readD();
        Heading = readD();
        Data = readD();
        return true;
    }

    @Override
    public void run() {
        // hmm todo
    }

    private int X, Y, Z, Heading, Data;
}
