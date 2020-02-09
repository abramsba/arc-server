package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

public class NewMacro extends GameReadPacket {
    public static int opcode = Opcodes.MAKE_MACRO;

    public NewMacro(Shard s) {
        super(s);
    }


    @Override
    protected boolean read() {
        id = readD();
        name = readS();
        desc = readS();
        acronym = readS();
        icon = readC();
        count = readC();
        for(int c = 0; c < count; c++) {

        }

        return true;
    }

    @Override
    public void run() {

    }

    int id, icon, count;
    String name, desc, acronym;
}
