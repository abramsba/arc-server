package be.leukspul.arc.server;

import be.leukspul.arc.server.config.Configuration;
import be.leukspul.arc.server.shard.Shard;
import net.sf.l2j.commons.mmocore.SelectorConfig;
import net.sf.l2j.commons.mmocore.SelectorThread;
import net.sf.l2j.util.IPv4Filter;

import java.net.InetAddress;

public class GameServer {

    public static void main(String [] args) throws Exception {
        System.out.println("Server is starting.");
        //Config.loadGameServer();
        new GameServer();
        System.out.println("Server is running.");
    }

    public GameServer() throws Exception {
        Cfg = new Configuration("./config.json");
        //ThreadPool.init();
        shard = new Shard();
        selectorConfig = new SelectorConfig();
        selectorConfig.MAX_READ_PER_PASS = Cfg.Mmo.MaxReadPerPass; // Config.MMO_MAX_READ_PER_PASS;
        selectorConfig.MAX_SEND_PER_PASS = Cfg.Mmo.MaxSendPerPass; //Config.MMO_MAX_READ_PER_PASS;
        selectorConfig.SLEEP_TIME = Cfg.Mmo.SleepTime;  // Config.MMO_SELECTOR_SLEEP_TIME;
        selectorConfig.HELPER_BUFFER_COUNT = Cfg.Mmo.BufferCount; // Config.MMO_HELPER_BUFFER_COUNT;
        handler = new GamePacketHandler(shard);
        selector = new SelectorThread<>(selectorConfig, handler, handler, handler, new IPv4Filter());
        try {
            bind = InetAddress.getByName(Cfg.Game.Bind);
        }
        catch (Exception e) {
        }
        selector.openServerSocket(bind, Cfg.Game.Port);
        selector.start();
    }

    private SelectorConfig selectorConfig;
    private SelectorThread<GameClient> selector;
    private GamePacketHandler handler;
    private InetAddress bind;
    private Shard shard;
    public static Configuration Cfg;
}
