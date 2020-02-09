package be.leukspul.arc.server.packet.client.login;

import be.leukspul.arc.server.packet.LoginReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.login.Ready;

public class SelectServer extends LoginReadPacket {
    public static int opcode = Opcodes.SELECT_SERVER;

    private int skey1;
    private int skey2;
    private int serverId;

    @Override
    protected boolean read() {
        if (_buf.remaining() >= 9) {
            skey1 = readD();
            skey2 = readD();
            serverId = readC();
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        getClient().send(new Ready());
    }
}
