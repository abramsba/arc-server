package be.leukspul.arc.server.packet.server.game;

import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class Html extends GameWritePacket {
    public Html(Shard s, Entity entity, String html) {
        super(s);
        this.html = html;
        this.entity = entity;
    }

    @Override
    protected void write() {
        writeC(0x0f);
        writeD(entity.id());
        writeS(html);
        writeD(0); // Item id?
    }

    private String html;
    private Entity entity;

}
