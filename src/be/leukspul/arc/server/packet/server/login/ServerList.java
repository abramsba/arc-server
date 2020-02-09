package be.leukspul.arc.server.packet.server.login;

import be.leukspul.arc.server.GameServer;
import be.leukspul.arc.server.LoginServer;
import be.leukspul.arc.server.packet.LoginWritePacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerList extends LoginWritePacket {
    public void write() {
        writeC(0x04);
        writeC(1); // Server size
        writeC(0); // Last Server
        writeC(1); // server id


        InetAddress clientIp = getClient().getConnection().getInetAddress();
        boolean local = false;
        boolean siteLocal = clientIp.isSiteLocalAddress();
        boolean anyLocal = clientIp.isAnyLocalAddress();
        boolean linkLocal = clientIp.isLinkLocalAddress();
        boolean loopback = clientIp.isLoopbackAddress();
        boolean multi = clientIp.isMulticastAddress();
        if (siteLocal || anyLocal || linkLocal || loopback || multi) {
            local = true;
        }

        byte [] serverIp = null;
        try {
            if (local) {
                serverIp = InetAddress.getByName(LoginServer.Cfg.Game.LocalHostname).getAddress();
            }
            else {
                serverIp = InetAddress.getByName(LoginServer.Cfg.Game.Hostname).getAddress();
            }
        }
        catch (UnknownHostException e) {
            serverIp = new byte[]{ 127, 0, 0, 1 };
            e.printStackTrace();
        }

        writeC(serverIp[0] & 0xff); // IP
        writeC(serverIp[1] & 0xff);
        writeC(serverIp[2] & 0xff);
        writeC(serverIp[3] & 0xff);

        writeD(LoginServer.Cfg.Game.Port); // Port
        writeC(0);    // Age limit
        writeC(1);  // PVP
        writeH(0);  // Current players
        writeH(1);  // Max Players
        writeC(1);  // Down or up
        writeD(0); // test server and clock?
        writeC(1);  // Brackets
    }
}
