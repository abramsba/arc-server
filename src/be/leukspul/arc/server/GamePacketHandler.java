package be.leukspul.arc.server;

import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.client.game.*;
import be.leukspul.arc.server.packet.server.game.Static;
import be.leukspul.arc.server.shard.Shard;
import net.sf.l2j.commons.mmocore.*;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class GamePacketHandler implements IPacketHandler<GameClient>, IClientFactory<GameClient>, IMMOExecutor<GameClient> {

    public GamePacketHandler(Shard shard) {
        this.shard = shard;
        Logout logout = new Logout(shard);
        packetsConnected = new HashMap<>();
        packetsConnected.put(ProtocolVersion.opcode, new ProtocolVersion(shard));
        packetsConnected.put(Authenticate.opcode, new Authenticate(shard));
        packetsAuthed = new HashMap<>();
        packetsAuthed.put(Logout.opcode, logout);
        packetsAuthed.put(CreateCharacter.opcode, new CreateCharacter(shard));
        packetsAuthed.put(SelectedCharacter.opcode, new SelectedCharacter(shard));
        packetsPlaying = new HashMap<>();
        packetsPlaying.put(EnterWorld.opcode, new EnterWorld(shard));
        packetsPlaying.put(Move.opcode, new Move(shard));
        packetsPlaying.put(ValidatePosition.opcode, new ValidatePosition(shard));
        packetsPlaying.put(SkillCoolTime.opcode, new SkillCoolTime(shard));
        packetsPlaying.put(Logout.opcode, logout);
        packetsPlaying.put(Action.opcode, new Action(shard));
        packetsPlaying.put(TargetCancel.opcode, new TargetCancel(shard));
        packetsPlaying.put(Use.opcode, new Use(shard));
        packetsPlaying.put(GetSkillList.opcode, new GetSkillList(shard));
        packetsPlaying.put(Chat.opcode, new Chat(shard));
        packetsPlaying.put(ViewMap.opcode, new ViewMap(shard));
        packetsPlaying.put(Bypass.opcode, new Bypass(shard));
        packetsPlaying.put(GmBypass.opcode, new GmBypass(shard));
        packetsPlaying.put(Appearing.opcode, new Appearing(shard));
        packetsPlayingExtra = new HashMap<>();
        packetsPlayingExtra.put(ManorList.opcode, new ManorList(shard));
    }

    @Override
    public GameClient create(MMOConnection<GameClient> con) {
        return new GameClient(con, shard);
    }

    @Override
    public ReceivablePacket<GameClient> handlePacket(ByteBuffer buf, GameClient client) {
        int opcode = buf.get() & 0xFF;
        ReceivablePacket<GameClient> packet = null;
        switch (client.state()) {
            case connected:
                packet = packetsConnected.get(opcode);
                break;
            case authenticated:
                packet = packetsAuthed.get(opcode);
                break;
            case playing:
                if (opcode == 0xd0) {
                    int opcode2 = buf.getShort() & 0xffff;
                    packet = packetsPlayingExtra.get(opcode2);
                }
                else {
                    packet = packetsPlaying.get(opcode);
                }
                break;
        }
        if (packet == null) {
            System.err.println("Handler for opcode "+opcode+" missing. User: "+client.name());
            client.send(new Static(shard));
        }
        return packet;
    }


    @Override
    public void execute(ReceivablePacket<GameClient> packet) {
        packet.getClient().execute((GameReadPacket)packet);
    }

    private Map<Integer, GameReadPacket> packetsConnected;
    private Map<Integer, GameReadPacket> packetsAuthed;
    private Map<Integer, GameReadPacket> packetsPlaying;
    private Map<Integer, GameReadPacket> packetsPlayingExtra;
    private Shard shard;

}