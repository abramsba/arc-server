package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.manager.Admin;
import be.leukspul.arc.manager.admin.AdminCommand;
import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.shard.Shard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Chat extends GameReadPacket {
    public static int opcode = Opcodes.SAY;

    public Chat(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        Text = readS();
        Type = readD();
        Target = (Type == 2) ? readS() : "";
        return true;
    }

    @Override
    public void run() {
        System.out.printf("%s\t\t%d\t\t%s\n", Text, Type, Target);
        if (Text.startsWith("~")) {
            List<String> parts = new LinkedList<>(Arrays.asList(Text.split(" ")));
            String command = parts.get(0).replace("~", "");
            parts.remove(0);
            try {
                AdminCommand cmd = Admin.Get(command, String.join(" ", parts));
                cmd.execute(getClient().entity());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private String Text;
    private int Type;
    private String Target;
}
