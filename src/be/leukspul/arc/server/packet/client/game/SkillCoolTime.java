package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.shard.Shard;

public class SkillCoolTime extends GameReadPacket {
    public static int opcode = 0x9d;

    public SkillCoolTime(Shard s) {
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
