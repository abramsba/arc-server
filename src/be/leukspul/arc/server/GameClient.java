package be.leukspul.arc.server;

import be.leukspul.arc.manager.Visibility;
import be.leukspul.arc.manager.player.PlayerController;
import be.leukspul.arc.server.packet.GameReadPacket;
import be.leukspul.arc.server.packet.GameWritePacket;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import net.sf.l2j.commons.mmocore.MMOClient;
import net.sf.l2j.commons.mmocore.MMOConnection;
import net.sf.l2j.commons.mmocore.ReceivablePacket;
import net.sf.l2j.gameserver.network.BlowFishKeygen;
import net.sf.l2j.gameserver.network.GameCrypt;
import net.sf.l2j.loginserver.SessionKey;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;

public class GameClient extends MMOClient<MMOConnection<GameClient>> implements Runnable {

    public enum State {
        connected,
        authenticated,
        playing
    }

    public GameClient(MMOConnection<GameClient> con, Shard shard) {
        super(con);
        this.shard = shard;
        crypt = new GameCrypt();
        key = BlowFishKeygen.getRandomKey();
        crypt.setKey(key);
        packets = new ArrayBlockingQueue<>(GameServer.Cfg.Mmo.QueueSize);
        packetLock = new ReentrantLock();
        exec = Executors.newSingleThreadScheduledExecutor();
        state = State.connected;
    }

    public void close(GameWritePacket packet) {
        getConnection().close(packet);
    }

    @Override
    public void run() {
        if (packetLock.tryLock()) {
            try {
                ReceivablePacket<GameClient> packet;
                while (!packets.isEmpty()) {
                    packet = packets.poll();
                    if (packet != null) {
                        packet.run();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                packetLock.unlock();
            }
        }
    }

    public void send(GameWritePacket packet) {
        getConnection().sendPacket(packet);
    }

    @Override
    public boolean decrypt(ByteBuffer buf, int size) {
        crypt.decrypt(buf.array(), buf.position(), size);
        return true;
    }

    @Override
    public boolean encrypt(ByteBuffer buf, int size) {
        crypt.encrypt(buf.array(), buf.position(), size);
        buf.position(buf.position() + size);
        return true;
    }

    @Override
    protected void onDisconnection() {
        shard.clientDisconnected(this);
    }

    @Override
    protected void onForcedDisconnection() {
        shard.clientDisconnected(this);
    }

    public void execute(GameReadPacket packet) {
        boolean added = packets.offer(packet);
        if (!added) {
            return;
        }
        if (packetLock.isLocked()) {
            return;
        }
        try {
            exec.execute(this);
            //ThreadPool.execute(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameCrypt crypt() {
        return crypt;
    }

    public byte[] key() {
        return key;
    }

    public SessionKey session() {
        return session;
    }

    public void session(SessionKey s) {
        session = s;
    }

    public State state() {
        return state;
    }

    public void state(State s) {
        this.state = s;
    }

    public int gameSessionId() {
        return gameSessionId;
    }

    public void gameSessionId(int id) {
        gameSessionId = id;
    }

    public void name(String n) {
        name = n;
    }

    public String name() {
        return name;
    }

    public void objectId(int id) {
        objectId = id;
    }

    public int objectId() {
        return objectId;
    }

    public Entity entity() {
        return entity;
    }

    public void entity(Entity e) {
        entity = e;
        if (e != null) {
            visibility = new Visibility(e);
        }
        else {
            visibility = null;
        }
    }

    public Visibility visibility() {
        return visibility;
    }

    public Shard shard() { return shard; }

    public void controller(PlayerController c) {
        controller = c;
    }

    public PlayerController controller() {
        return controller;
    }

    private SessionKey session;
    private GameCrypt crypt;
    private byte[] key;
    private ArrayBlockingQueue<GameReadPacket> packets;
    private ReentrantLock packetLock; // = new ReentrantLock();
    private State state;
    private int gameSessionId;
    private String name;
    private Shard shard;
    private int objectId;
    private Entity entity;
    private Visibility visibility;
    private PlayerController controller;
    private ScheduledExecutorService exec;
    private boolean bye;
}

