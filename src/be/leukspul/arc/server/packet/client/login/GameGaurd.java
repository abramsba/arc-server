package be.leukspul.arc.server.packet.client.login;

import be.leukspul.arc.server.packet.LoginReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
public class GameGaurd extends LoginReadPacket {
    public static int opcode = Opcodes.GAME_GAURD_AUTHENTICATE;

    @Override
    protected boolean read() {
        if (super._buf.remaining() >= 20) {
            data = new int [] { 0, 0, 0, 0 };
            sessionId = readD();
            for (int i = 0; i < 4; i++) {
                data[i] = readD();
            }
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        getClient().send(new be.leukspul.arc.server.packet.server.login.GameGaurd(sessionId));
    }

    private int sessionId;
    private int [] data;
}
