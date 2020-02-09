package be.leukspul.arc.server;

import be.leukspul.arc.server.packet.LoginReadPacket;
import be.leukspul.arc.server.packet.client.login.Authenticate;
import be.leukspul.arc.server.packet.client.login.GameGaurd;
import be.leukspul.arc.server.packet.client.login.SelectServer;
import net.sf.l2j.commons.mmocore.IPacketHandler;
import net.sf.l2j.commons.mmocore.ReceivablePacket;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class LoginPacketHandler implements IPacketHandler<LoginClient> {

    public LoginPacketHandler() {
        routes = new HashMap<>();
        routes.put(GameGaurd.opcode, GameGaurd.class);
        routes.put(Authenticate.opcode, Authenticate.class);
        routes.put(SelectServer.opcode, SelectServer.class);
    }

    @Override
    public ReceivablePacket<LoginClient> handlePacket(ByteBuffer buf, LoginClient client) {
        int opcode = buf.get() & 0xFF;
        Class<LoginReadPacket> route = routes.get(opcode);
        if (route != null) {
            try {
                return route.newInstance();
            } catch (Exception e) {
            }
        }
        return null;
    }

    private Map<Integer, Class> routes;
}
