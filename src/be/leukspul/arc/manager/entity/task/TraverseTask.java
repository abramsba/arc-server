package be.leukspul.arc.manager.entity.task;

import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.graph.Node;
import be.leukspul.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class TraverseTask extends BaseTask {

    public TraverseTask(Shard shard, Entity entity, Node start, Node target, List<Node> visited) {
        super(shard, entity);
        this.start = start;
        this.target = target;
        this.score = entity.position().dist2(target.vec3());
        if (visited == null) {
            this.visited = new ArrayList<>();
        }
        else {
            this.visited = visited;
        }
    }

    @Override
    public void onStart() {
        // IF the dist2 is less than the radius of the node they want to walk to, than
        // they reached the target so stop the loop
        if (this.start == this.target) {
            MoveTask move = new MoveTask(shard, entity, target.vec3());
            shard.entityManager().add(move);
            this.stop = true;
            return;
        }
    }

    @Override
    public void execute() {
        if (!stop) {
            Node bestMatch; //bestMatch(start, target);
            if (start.links() == 1) {
                bestMatch = start.getLink(0);
            }
            else {
                bestMatch = bestMatch(start, target);
            }
            visited.add(bestMatch);
            MoveTask move = new MoveTask(shard, entity, bestMatch.vec3());
            TraverseTask traverse = new TraverseTask(shard, entity, bestMatch, target, visited);
            shard.entityManager().add(move);
            shard.entityManager().add(traverse);
        }
    }

    public Node bestMatch(Node current, Node target) {
        if (current.links() == 1) {
            return current.getLink(0);
        }
        double lowest = Double.MAX_VALUE;
        double forwardest = Double.MAX_VALUE;
        Node bestForward = null;
        Node bestMatch = null;
        for (int i = 0; i < current.links(); i++) {
            Node next = current.getLink(i);
            if (!visited.contains(next)) {
                double dist2 = next.vec3().dist2(target.vec3());
                Vector3 np = next.vec3();
                Vector3 cp = current.vec3();
                double dir = Math.abs(entity.direction().radians() - Math.atan2(np.y() - cp.y(), np.x() - cp.x()));
                if (dir < forwardest) {
                    bestForward = next;
                    forwardest = dir;
                }
                if (dist2 < lowest) {
                    lowest = dist2;
                    bestMatch = next;
                }
            }
        }
        while (bestMatch == null) {
            visited.remove(0);
            bestMatch = bestMatch(current, target);
        }
        return bestMatch;
    }

    private Node start;
    private Node target;
    private List<Node> visited;
    private double score;
}
