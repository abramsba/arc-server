package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class SkillCoolTime extends GameWritePacket {
    public SkillCoolTime(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0xc1);
        writeD(0);
    }
}
