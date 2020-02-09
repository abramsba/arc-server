package be.leukspul.arc.server;

import be.leukspul.arc.server.packet.LoginWritePacket;
import be.leukspul.math.Rand;
import net.sf.l2j.commons.mmocore.MMOClient;
import net.sf.l2j.commons.mmocore.MMOConnection;
import net.sf.l2j.commons.random.Rnd;
import net.sf.l2j.loginserver.SessionKey;
import net.sf.l2j.loginserver.crypt.LoginCrypt;
import net.sf.l2j.loginserver.crypt.ScrambledKeyPair;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.interfaces.RSAPrivateKey;

public class LoginClient extends MMOClient<MMOConnection<LoginClient>> {

    public LoginClient(MMOConnection<LoginClient> con) {
        super(con);
        key = LoginServer.getRandomKey();
        keyPair = LoginServer.getRandomKeyPair();
        rsaKey = (RSAPrivateKey)keyPair.getKeyPair().getPrivate();
        session = Rnd.nextInt();
        crypt = new LoginCrypt();
        crypt.setKey(key);
        sessionKey = new SessionKey(Rand.getInt(), Rand.getInt(), Rand.getInt(), Rand.getInt());
    }

    @Override
    public boolean decrypt(ByteBuffer buf, int size) {
        try {
            return crypt.decrypt(buf.array(), buf.position(), size);
        } catch (IOException e) {
        }
        return false;
    }

    @Override
    public boolean encrypt(ByteBuffer buf, int size) {
        try {
            int offset = buf.position();
            size = crypt.encrypt(buf.array(), offset, size);
            buf.position(offset + size);
            return true;
        } catch (IOException e) {
        }
        return false;
    }

    @Override
    protected void onDisconnection() {

    }

    @Override
    protected void onForcedDisconnection() {

    }

    public void send(LoginWritePacket packet) {
        getConnection().sendPacket(packet);
    }

    public ScrambledKeyPair keyPair() {
        return keyPair;
    }

    public RSAPrivateKey rsaKey() {
        return rsaKey;
    }

    public byte [] key() {
        return key;
    }

    public int session() {
        return session;
    }

    public SessionKey sessionKey() { return sessionKey; }

    private RSAPrivateKey rsaKey;
    private LoginCrypt crypt;
    private ScrambledKeyPair keyPair;
    private byte [] key;
    private int session;
    private SessionKey sessionKey;


}
