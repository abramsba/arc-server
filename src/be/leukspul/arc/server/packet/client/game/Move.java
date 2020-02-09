package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.Static;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.math.Vector3;

public class Move extends GameReadPacket {
    public static int opcode = Opcodes.MOVE_BACKWARDS;

    public Move(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        int tx = readD();
        int ty = readD();
        int tz = readD();
        int ox = readD();
        int oy = readD();
        int oz = readD();
        origin = new Vector3(ox, oy, oz);
        target = new Vector3(tx, ty, tz);
        return true;
    }

    @Override
    public void run() {
        getClient().controller().move(origin, target);
        getClient().send(new Static(shard()));
    }

    Vector3 origin;
    Vector3 target;


}
