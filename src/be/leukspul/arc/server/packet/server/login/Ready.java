package be.leukspul.arc.server.packet.server.login;

import be.leukspul.arc.server.packet.LoginWritePacket;

public class Ready extends LoginWritePacket {

    @Override
    protected void write() {
        writeC(0x07);
        writeD(getClient().sessionKey().playOkID1);
        writeD(getClient().sessionKey().playOkID2);
    }
}
