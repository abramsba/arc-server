package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class Shortcuts extends GameWritePacket {
    public Shortcuts(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0x45);
        writeD(0); // Length

        /*
        for shortcut
            writeD(0); // type
            writeD(0); // slot * page * 12

            when item:
                writeD(0); // id
                writeD(0); // char type
                writeD(0); // shared reuse group


         */
    }
}
