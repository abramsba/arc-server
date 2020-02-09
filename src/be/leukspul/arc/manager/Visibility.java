package be.leukspul.arc.manager;

import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.packet.server.game.*;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;
import be.leukspul.data.ecs.component.Door;
import be.leukspul.data.ecs.component.RefClient;
import be.leukspul.data.ecs.component.RefShard;
import be.leukspul.data.tree.quad.TreeNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Visibility {

    public Visibility(Entity e) {
        Known = Collections.synchronizedList(new LinkedList<>());
        E = e;
        S = E.get(RefShard.class).Shard;
        C = E.get(RefClient.class).Client;
    }

    public synchronized void seen(Entity e) {
        if (!Known.contains(e) && E != e) {
            Known.add(e);
            if (e.has(Door.class)) {
                C.send(new DoorInfo(S, e));
                C.send(new DoorStatus(S, e));
            } else if (e.has(Avatar.class)) {
                C.send(new EntityInfo(S, e));
                C.send(new EntityMove(S, e, (int)e.position().x(), (int)e.position().y(), (int)e.position().z()));
            }
            if (e.has(RefClient.class)) {
                e.get(RefClient.class).Client.visibility().seen(E);
            }
        }
    }

    // Objects that leave notify client
    public synchronized void forget(Entity e) {
            if (Known.contains(e)) {
                Known.remove(e);
                C.send(new DeleteEntity(S, e));
            }
    }

    public synchronized void bye() {
        for (ListIterator<Entity> iter = Known.listIterator(); iter.hasNext(); ) {
            Entity e = iter.next();
            if (e.has(RefClient.class)) {
                e.get(RefClient.class).Client.visibility().forget(E);
            }
        }
    }

    public Entity find(int id) {
        for(Entity known : Known) {
            if (known.id() == id) {
                return known;
            }
        }
        return null;
    }

    public synchronized void inspect(List<TreeNode<Entity>> found) {
        double max_range = Math.pow(ViewDistance, 2);
        for (ListIterator<Entity> iter = Known.listIterator(); iter.hasNext(); ) {
            Entity next = iter.next();
            double x2 = Math.pow(next.position().x() - E.position().x(), 2);
            double y2 = Math.pow(next.position().x() - E.position().y(), 2);
            double dist = x2 + y2;
            if (dist > max_range) {
                iter.remove();
                C.send(new DeleteEntity(S, next));
                if (next.has(RefClient.class)) {
                    next.get(RefClient.class).Client.visibility().forget(E);
                }
            }
        }

        for (TreeNode<Entity> node : found) {
            if (!Known.contains(node.get())) {
                seen(node.get());
            }
        }
    }

    private Entity E;
    private Shard S;
    private GameClient C;
    private List<Entity> Known;

    public static int ViewDistance = 20000;
}
