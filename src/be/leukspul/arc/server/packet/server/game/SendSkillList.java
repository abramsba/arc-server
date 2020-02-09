package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.library.model.SkillTemplate;
import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Skills;

import java.util.Set;

public class SendSkillList extends GameWritePacket {
    public SendSkillList(Shard s, Entity e) {
        super(s);
        E = e;
    }

    @Override
    protected void write() {
        Set<SkillTemplate> skills = E.get(Skills.class).Skills;
        writeC(0x58);
        writeD(skills.size());
        for (SkillTemplate skill : skills) {
            writeD(skill.Type.equals("OP_PASSIVE") ? 1 : 0);
            writeD(skill.Level);
            writeD(skill.SkillId);
            writeC(0);
        }
    }

    private Entity E;
}
