package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Attributes;
import be.leukspul.data.ecs.component.Avatar;
import be.leukspul.data.ecs.component.Vitals;

public class CharacterSelected extends GameWritePacket {

    public CharacterSelected(Shard shard) {
        super(shard);
    }

    @Override
    protected void write() {
        Entity entity = getClient().entity();
        Vitals vitals = entity.get(Vitals.class);
        Avatar avatar = entity.get(Avatar.class);
        Attributes attr = entity.get(Attributes.class);

        writeC(0x15);
        writeS(avatar.Name); // Name
        writeD(entity.id()); // object id
        writeS(avatar.Title); // Title
        writeD(getClient().gameSessionId()); // session id
        writeD(0); // clan id
        writeD(0); // ?
        writeD(avatar.Gender); // gender
        writeD(avatar.Race); // race
        writeD(avatar.Class); // class id
        writeD(1); // ?
        writeD((int)entity.position().x()); // x
        writeD((int)entity.position().y()); // y
        writeD((int)entity.position().z()); // z
        writeF(vitals.Health); // hp
        writeF(0); // mp
        writeD(0); // sp
        writeD(0); // exp
        writeD(1); // level
        writeD(0); // karma
        writeD(0); // pk
        writeD(attr.Intelligence); // int
        writeD(attr.Strength); // str
        writeD(attr.Constitution); // con
        writeD(attr.Mentality); // men
        writeD(attr.Dexterity); // dex
        writeD(attr.Wisdom); // wit

        for (int i = 0; i < 30; i++)
        {
            writeD(0x00);
        }

        writeD(0x00); // c3 work
        writeD(0x00); // c3 work

        writeD(0); // game time

        writeD(0x00); // c3

        writeD(avatar.Class); // class id

        writeD(0x00); // c3 InspectorBin
        writeD(0x00); // c3
        writeD(0x00); // c3
        writeD(0x00); // c3
    }

}
