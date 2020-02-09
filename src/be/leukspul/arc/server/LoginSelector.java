package be.leukspul.arc.server;

import be.leukspul.arc.server.packet.server.login.Initialize;
import net.sf.l2j.commons.mmocore.*;

import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoginSelector implements IMMOExecutor<LoginClient>, IClientFactory<LoginClient>, IAcceptFilter {

    public LoginSelector() {
        pool = new ThreadPoolExecutor(4, 6, 15L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public boolean accept(SocketChannel sc) {
        return true;
    }

    @Override
    public LoginClient create(MMOConnection<LoginClient> con) {
        LoginClient client = new LoginClient(con);
        client.send(new Initialize(client));
        return client;
    }

    @Override
    public void execute(ReceivablePacket<LoginClient> packet) {
        pool.execute(packet);
    }

    private final ThreadPoolExecutor pool;
}
