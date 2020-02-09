package be.leukspul.arc.server.shard;

import be.leukspul.arc.library.Library;
import be.leukspul.arc.library.model.DoorTemplate;
import be.leukspul.arc.manager.Visibility;
import be.leukspul.arc.manager.entity.EntityFactory;
import be.leukspul.arc.manager.entity.EntityManager;
import be.leukspul.arc.manager.entity.EntityOptions;
import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.packet.server.game.CharacterSelected;
import be.leukspul.arc.server.packet.server.game.DeleteEntity;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefClient;
import be.leukspul.data.ecs.component.RefTree;
import be.leukspul.data.graph.Graph;
import be.leukspul.data.graph.Node;
import be.leukspul.data.tree.quad.TreeNode;
import be.leukspul.data.tree.quad.QuadTree;
import be.leukspul.math.Vector3;
import net.sf.l2j.gameserver.geoengine.GeoEngine;
import net.sf.l2j.gameserver.geoengine.GeoEnginePathfinding;
import net.sf.l2j.gameserver.geoengine.geodata.GeoStructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Shard {

    public Shard() throws SQLException {
        currentId = FirstID;
        System.out.println("Creating shard and initializing GeoEngine");
        geoEngine = (GeoEnginePathfinding) GeoEngine.getInstance();
        String content = null;
        try {
            content = new Scanner(new File("data/graph/giran.json")).useDelimiter("\\Z").next();
            //graph = Graph.fromJSON(content);
            graphTree = new QuadTree<TreeNode<Node>>(null, null, GeoStructure.WORLD_X_MIN, GeoStructure.WORLD_Y_MIN, GeoStructure.WORLD_WIDTH, GeoStructure.WORLD_HEIGHT, 512, (Class) ArrayList.class);
            graph = Graph.loadIntoQuadTree(content, graphTree);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (!geoEngine.ready()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        clients = new ConcurrentHashMap<>();
        entities = new ConcurrentHashMap<>();
        tree = new QuadTree<TreeNode<Entity>>(null, null, GeoStructure.WORLD_X_MIN, GeoStructure.WORLD_Y_MIN, GeoStructure.WORLD_WIDTH, GeoStructure.WORLD_HEIGHT, 512, (Class) LinkedList.class);
        Lib = new Library();
        entityManager = new EntityManager(2048);
        initializeDoors();
        System.out.println("Shard ready");
    }

    public EntityManager entityManager() {
        return entityManager;
    }

    public void clientConnected(GameClient client) {
        clients.put(client.name(), client);
    }

    public void clientDisconnected(GameClient client) {
        if (client.entity() != null) {
            client.visibility().bye();
            entities.remove(client.entity().id());
            RefTree n = client.entity().get(RefTree.class);
            tree.remove(n.treeNode);
            client.entity(null);
        }
        clients.remove(client.name());
    }

    public Entity entity(int id) {
        return entities.get(id);
    }

    public void playerEntered(GameClient client, int slot) {
        Vector3 spawnPosition = new Vector3(87069, 148663, -3427);
        EntityOptions options = new EntityOptions();
        Entity player = EntityFactory.createPlayer(this, client.name(), lib().PcTemplates().random(), spawnPosition, options);
        RefClient refClient = new RefClient(client);
        player.add(refClient);
        client.entity(player);
        client.objectId(player.id());
        client.state(GameClient.State.playing);
        registerEntity(player);
        client.send(new CharacterSelected(this));
    }

    public void whoThere(Entity e) {
        RefClient c = e.get(RefClient.class);
        List<TreeNode<Entity>> found = tree.circle(e.position().x(), e.position().y(), Visibility.ViewDistance, ref -> e != ref.get());
        c.Client.visibility().inspect(found);
    }

    public GeoEnginePathfinding geoEngine() { return geoEngine; }

    public void registerEntity(Entity e) {
        TreeNode<Entity> node = new TreeNode<>(e, e.position().x(), e.position().y());
        e.add(new RefTree(node));
        tree.add(node);
        entities.put(e.id(), e);
    }

    public void deleteEntity(Entity e) {
        entityManager.stop(e);
        tree.remove(e.get(RefTree.class).treeNode);
        entities.remove(e.id());
        Broadcast.ToAllClients(this, new DeleteEntity(this, e));
    }

    public int nextID() {
        synchronized (this) {
            currentId++;
        }
        return currentId;
    }

    public List<GameClient> clients() {
        List<GameClient> gcs = new ArrayList<>();
        for (String key : clients.keySet()) {
            gcs.add(clients.get(key));
        }
        return gcs;
    }

    public List<GameClient> clients(Vector3 pos, double radius) {
        List<GameClient> gcs = new ArrayList<>();
        double r2 = (radius * radius);
        for (String key : clients.keySet()) {
            Entity player = clients.get(key).entity();
            if (player != null) {
                double dx = Math.pow(pos.x() - player.position().x(), 2);
                double dy = Math.pow(pos.y() - player.position().y(), 2);
                if (r2 >= (dx + dy)) {
                    gcs.add(clients.get(key));
                }
            }
        }
        return gcs;
    }

    public boolean clientPresent(Vector3 pos, double radius) {
        for (String key : clients.keySet()) {
            double r2 = (radius * radius);
            Entity player = clients.get(key).entity();
            if (player != null) {
                double dx = Math.pow(pos.x() - player.position().x(), 2);
                double dy = Math.pow(pos.y() - player.position().y(), 2);
                if (r2 >= (dx + dy)) {
                    return true;
                }
            }
        }
        return false;
    }

    public QuadTree<TreeNode<Entity>> tree() {
        return tree;
    }

    public QuadTree<TreeNode<Node>> gps() { return graphTree; }

    public Library lib() {
        return Lib;
    }

    public Graph graph() { return graph; }

    private int currentId;
    private Map<String, GameClient> clients;
    private Map<Integer, Entity> entities;
    private GeoEnginePathfinding geoEngine;
    private QuadTree<TreeNode<Entity>> tree;
    private QuadTree<TreeNode<Node>> graphTree;
    private Library Lib;
    private Graph graph;

    private EntityManager entityManager;

    private void initializeDoors() {
        for(DoorTemplate tpl : lib().DoorTemplates().all()) {
            Entity newDoor = EntityFactory.createDoor(this, tpl);
            registerEntity(newDoor);
        }
    }

    private static final int FirstID = 0x10000000;
}
