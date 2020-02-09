package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.PlayerInGame;
import be.leukspul.arc.server.packet.server.game.SelectCharacterInfo;
import be.leukspul.arc.server.shard.Shard;
import net.sf.l2j.loginserver.SessionKey;

public class Authenticate extends GameReadPacket {
    public static int opcode = Opcodes.AUTH_LOGIN;

    public Authenticate(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        name = readS().toLowerCase();
        key1 = readD();
        key2 = readD();
        login1 = readD();
        login2 = readD();
        return true;
    }

    @Override
    public void run() {
        getClient().state(GameClient.State.authenticated);
        getClient().name(name);
        shard().clientConnected(getClient());
        getClient().send(new PlayerInGame(shard(), "Testing"));
        getClient().send(new SelectCharacterInfo(shard()));
        getClient().session(new SessionKey(key1, key2, login1, login2));
    }

    private String name;
    private int key1;
    private int key2;
    private int login1;
    private int login2;
}
