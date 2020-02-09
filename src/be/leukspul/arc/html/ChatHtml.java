package be.leukspul.arc.html;

import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.packet.server.game.Html;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefClient;
import be.leukspul.data.ecs.component.RefShard;

public abstract class ChatHtml {

    public abstract String Html();

    public void Display(Entity from, Entity to) {
        Shard s = to.get(RefShard.class).Shard;
        GameClient gc = to.get(RefClient.class).Client;
        gc.send(new Html(s, from, Html()));
    }

    protected String html;


}
