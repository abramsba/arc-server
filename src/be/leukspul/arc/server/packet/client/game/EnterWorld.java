package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.manager.player.PlayerController;
import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.*;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;

public class EnterWorld extends GameReadPacket {
    public static int opcode = Opcodes.ENTER_WORLD;

    public EnterWorld(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        return true;
    }

    @Override
    public void run() {
        Entity e = getClient().entity();
        Shard s = shard();
        getClient().controller(new PlayerController(e));
        getClient().send(new PlayerInfo(s, e));
        getClient().send(new PlayerHenna(s));
        getClient().send(new PlayerFriends(s));
        getClient().send(new EntityItems(s, e,false));
        getClient().send(new Shortcuts(s));
        getClient().send(new StorageMax(s));
        getClient().send(new PlayerStatus(s));
        getClient().send(new SendSkillList(s, e));
        getClient().send(new QuestList(s));
        getClient().send(new be.leukspul.arc.server.packet.server.game.SkillCoolTime(s));
        getClient().send(new SetTime(s));
        getClient().send(new Static(s));
        s.whoThere(e);
    }
}
