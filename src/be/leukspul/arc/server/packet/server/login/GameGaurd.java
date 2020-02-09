package be.leukspul.arc.server.packet.server.login;

import be.leukspul.arc.server.packet.LoginWritePacket;

public class GameGaurd extends LoginWritePacket {

    public GameGaurd(int response) {
        this.response = response;
    }

    protected void write() {
        writeC(0x0b);
        writeD(response);
        writeC(0x00);
        writeC(0x00);
        writeC(0x00);
        writeC(0x00);
    }

    private int response;

}
