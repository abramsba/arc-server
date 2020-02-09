package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class SelectCharacterInfo extends GameWritePacket {

    public SelectCharacterInfo(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0x13);
        writeD(1); // size
        // for each char

            writeS("Testing"); // name
            writeD(0); // charId
            writeS("Testing"); // login name
            writeD(0); // sessionId
            writeD(0); // clan id
            writeD(0); // ?
            writeD(0); // gender
            writeD(0); // race
            writeD(0); // class id
            writeD(1); // ?
            writeD(0); // xpos
            writeD(0); // ypos
            writeD(0); // zpos
            writeD(0); // currentHP
            writeD(0); // currentMP
            writeD(0); // sp
            writeD(0); // exp
            writeD(0); // level
            writeD(0); // karma
            writeD(0); // pks
            writeD(0); // pvp kills
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0); // paperdoll hair
            writeD(0); // paperdoll right ear
            writeD(0); // paperdoll left ear
            writeD(0); // paperdoll neck
            writeD(0); // paperdoll rfinger
            writeD(0); // paperdoll lfinger
            writeD(0); // paperdoll head
            writeD(0); // paperdoll rhand
            writeD(0); // paperdoll lhand
            writeD(0); // paperdoll gloves
            writeD(0); // paperdoll chest
            writeD(0); // paperdoll legs
            writeD(0); // paperdoll feet
            writeD(0); // paperdoll back
            writeD(0); // paperdoll rhand
            writeD(0); // paperdoll hair
            writeD(0); // paperdoll face
            writeD(0); // paperdoll hair
            writeD(0); // paperdoll right ear
            writeD(0); // paperdoll left ear
            writeD(0); // paperdoll neck
            writeD(0); // paperdoll rfinger
            writeD(0); // paperdoll lfinger
            writeD(0); // paperdoll head
            writeD(0); // paperdoll rhand
            writeD(0); // paperdoll lhand
            writeD(0); // paperdoll gloves
            writeD(0); // paperdoll chest
            writeD(0); // paperdoll legs
            writeD(0); // paperdoll feet
            writeD(0); // paperdoll back
            writeD(0); // paperdoll rhand
            writeD(0); // paperdoll hair
            writeD(0); // paperdoll face
            writeD(0); // hair style
            writeD(0); // hair color
            writeD(0); // face
            writeF(0); // max hp
            writeF(0); // max mp
            writeD(0); // access level
            writeD(0); // class id
            writeD(0); // active
            writeC(0); // enchant
            writeD(0); // aug id
    }
}
