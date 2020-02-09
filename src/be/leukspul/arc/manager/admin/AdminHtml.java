package be.leukspul.arc.manager.admin;

import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.packet.server.game.Html;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefClient;
import be.leukspul.data.ecs.component.RefShard;

import java.io.StringWriter;

public class AdminHtml extends AdminCommand {

    public AdminHtml(String input) {
        super(input);
    }

    @Override
    public void execute(Entity admin) {
        GameClient gc = admin.get(RefClient.class).Client;
        Shard shard = admin.get(RefShard.class).Shard;
        StringWriter sw = new StringWriter();

        gc.send(new Html(shard, admin, "<html><body><b>Hi</b></body></html>"));
    }
}
