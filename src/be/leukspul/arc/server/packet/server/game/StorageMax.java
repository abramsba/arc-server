package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;

public class StorageMax extends GameWritePacket {
    public StorageMax(Shard s) {
        super(s);
    }

    @Override
    protected void write() {
        writeC(0xfe);
        writeH(0x2e);
        writeD(0); // Inventory Limit
        writeD(0); // Warehouse limit
        writeD(0); // Freight Limit
        writeD(0); // Private sell limit
        writeD(0); // Private buy limit
        writeD(0); // dwarf recipe limit
        writeD(0); // common recipe limit
    }
}
