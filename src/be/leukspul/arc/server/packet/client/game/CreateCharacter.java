package be.leukspul.arc.server.packet.client.game;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.Opcodes;
import be.leukspul.arc.server.packet.server.game.CreateCharacterFail;
import be.leukspul.arc.server.shard.Shard;

public class CreateCharacter extends GameReadPacket {
    public static int opcode = Opcodes.CREATE_CHARACTER;

    public CreateCharacter(Shard s) {
        super(s);
    }

    @Override
    protected boolean read() {
        return true;
    }

    @Override
    public void run() {
        getClient().send(new CreateCharacterFail(shard()));
    }
}
