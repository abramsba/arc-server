package be.leukspul.arc.server.packet;

import be.leukspul.arc.server.LoginClient;
import net.sf.l2j.commons.mmocore.ReceivablePacket;

public abstract class LoginReadPacket extends ReceivablePacket<LoginClient> {
    public static int opcode = -1;
}
