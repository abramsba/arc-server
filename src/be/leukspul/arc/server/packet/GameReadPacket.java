package be.leukspul.arc.server.packet;

import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.shard.Shard;
import net.sf.l2j.commons.mmocore.ReceivablePacket;

public abstract class GameReadPacket extends ReceivablePacket<GameClient> {
    public static int opcode = -1;
    public GameReadPacket(Shard s) {
        shard = s;
    }
    public Shard shard() {
        return shard;
    }
    private Shard shard;
}
