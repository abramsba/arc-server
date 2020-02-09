package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.manager.Admin;
import be.leukspul.arc.manager.admin.AdminCommand;
import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GmBypass extends GameReadPacket {
    public static int opcode = Opcodes.SEND_BYPASS_BUILD_CMD;

    public GmBypass(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        command = readS();
        return true;
    }

    @Override
    public void run() {
        String [] parts = command.split(" ");
        List<String> args = new LinkedList<>(Arrays.asList(parts));
        args.remove(0);
        try {
            AdminCommand cmd = Admin.Get(parts[0], String.join(" ", args));
            cmd.execute(getClient().entity());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String command;
}
