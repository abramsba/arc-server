package be.leukspul.arc.server.packet.client.login;


import be.leukspul.arc.server.packet.LoginReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.login.ServerList;

import javax.crypto.Cipher;

public class Authenticate extends LoginReadPacket {
    public static int opcode = Opcodes.LOGIN;

    public Authenticate() {
        raw = new byte[128];
    }

    @Override
    protected boolean read() {
        if (_buf.remaining() >= 128) {
            readB(raw);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/nopadding");
            cipher.init(Cipher.DECRYPT_MODE, getClient().rsaKey());
            byte [] decrypted = cipher.doFinal(raw, 0x00, 0x80);
            String username = new String(decrypted, 0x5e, 14).trim();
            String password = new String(decrypted, 0x6c, 16).trim();
            getClient().send(new ServerList());
        } catch (Exception e) {
        }

    }

    private byte [] raw;
}
