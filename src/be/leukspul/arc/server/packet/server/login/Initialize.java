package be.leukspul.arc.server.packet.server.login;

import be.leukspul.arc.server.LoginClient;
import be.leukspul.arc.server.packet.LoginWritePacket;

public class Initialize extends LoginWritePacket {
    private int sessionId;
    private byte [] publicKey;
    private byte [] blowfishKey;
    public Initialize(LoginClient client) {
        sessionId = 0;
        publicKey = client.keyPair().getScrambledModulus();
        blowfishKey = client.key();
    }

    @Override
    protected void write() {
        writeC(0x00); // init packet id
        writeD(sessionId); // session id
        writeD(0x0000c621); // protocol revision
        writeB(publicKey); // RSA Public Key
        writeD(0x29DD954E);
        writeD(0x77C39CFC);
        writeD(0x97ADB620);
        writeD(0x07BDE0F7);
        writeB(blowfishKey); // BlowFish key
        writeC(0x00); // null termination
    }
}
