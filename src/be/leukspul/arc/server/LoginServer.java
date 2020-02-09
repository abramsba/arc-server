package be.leukspul.arc.server;

import be.leukspul.arc.server.config.Configuration;
import be.leukspul.math.Rand;
import net.sf.l2j.commons.mmocore.SelectorConfig;
import net.sf.l2j.commons.mmocore.SelectorThread;
import net.sf.l2j.commons.random.Rnd;
import net.sf.l2j.loginserver.crypt.ScrambledKeyPair;

import java.net.InetAddress;
import java.security.KeyPairGenerator;
import java.security.spec.RSAKeyGenParameterSpec;

public class LoginServer {

    public static void main(String [] args) throws Exception {
        new LoginServer();
    }

    public LoginServer() throws Exception {
        Cfg = new Configuration("./config.json");
        try {
            bind = InetAddress.getByName(Cfg.Login.Hostname);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        selectorConfig = new SelectorConfig();
        selectorConfig.MAX_READ_PER_PASS = Cfg.Mmo.MaxReadPerPass; //Config.MMO_MAX_READ_PER_PASS;
        selectorConfig.MAX_SEND_PER_PASS = Cfg.Mmo.MaxSendPerPass; //Config.MMO_MAX_SEND_PER_PASS;
        selectorConfig.SLEEP_TIME = Cfg.Mmo.SleepTime; //Config.MMO_SELECTOR_SLEEP_TIME;
        selectorConfig.HELPER_BUFFER_COUNT = Cfg.Mmo.BufferCount; // Config.MMO_HELPER_BUFFER_COUNT;
        selectorHelper = new LoginSelector();
        handler = new LoginPacketHandler();

        // RSA Key Pairs
        int keyPairCount = 10;
        keyPairs = new ScrambledKeyPair[keyPairCount];
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        RSAKeyGenParameterSpec keySpecs = new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4);
        keyGen.initialize(keySpecs);
        for (int i = 0; i < keyPairCount; i++) {
            keyPairs[i] = new ScrambledKeyPair(keyGen.generateKeyPair());
        }

        // Blowfish Keys
        int keyCount = 20;
        int byteCount = 16;
        keys = new byte[keyCount][byteCount];
        for (int i = 0; i < keyCount; i++) {
            for (int j = 0; j < byteCount; j++) {
                keys[i][j] = (byte)(Rand.getInt(255) + 1);
            }
        }

        selector = new SelectorThread<>(selectorConfig, selectorHelper, handler, selectorHelper, selectorHelper);
        selector.openServerSocket(bind, Cfg.Login.Port);
        selector.start();
    }


    public static ScrambledKeyPair getRandomKeyPair() {
        return keyPairs[Rnd.get(keyPairs.length)];
    }

    public static byte [] getRandomKey() {
        return keys[Rnd.get(keys.length)];
    }

    private LoginPacketHandler handler;
    private InetAddress bind;
    private SelectorConfig selectorConfig;
    private LoginSelector selectorHelper;
    private SelectorThread<LoginClient> selector;

    private static ScrambledKeyPair [] keyPairs;
    private static byte [][] keys;
    public static Configuration Cfg;

}
