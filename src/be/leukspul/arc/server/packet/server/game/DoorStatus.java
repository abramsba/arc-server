package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Door;

public class DoorStatus extends GameWritePacket {

    public DoorStatus(Shard s, Entity d) {
        super(s);
        door = d;
    }

    @Override
    protected void write() {
        Door d = door.get(Door.class);
        writeC(0x4d);
        writeD(door.id());
        writeD(d.Open ? 0: 1);
        writeD(0); // Damage
        writeD(0); // Attackable
        writeD(d.Template.Id);
        writeD(1); // Max HP
        writeD(1); // Current HP
    }

    private Entity door;
}
