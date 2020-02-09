package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Door;

public class DoorInfo extends GameWritePacket {
    public DoorInfo(Shard s, Entity d) {
        super(s);
        door = d;
    }

    @Override
    protected void write() {
        Door d = door.get(Door.class);
        writeC(0x4c);
        writeD(door.id());
        writeD(door.get(Door.class).Template.Id);
        writeD(0); // can attack
        writeD(1); // can target
        writeD(d.Open ? 0 : 1);
        writeD(1); // max hp
        writeD(1); // cur hp
        writeD(0); // show hp
        writeD(0); // Damage?
    }

    private Entity door;
}
