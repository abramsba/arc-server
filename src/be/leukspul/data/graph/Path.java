package be.leukspul.data.graph;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Path implements Comparable<Path> {

    public List<Node> points;
    public double weight;

    @Override
    public int compareTo(Path o) {
        if (this.weight > o.weight) {
            return 1;
        }
        else if (this.weight == o.weight) {
            return 0;
        }
        return -1;
    }

    // The points in this node are converted to an integer array based on their index
    public JSONObject toJSON(Graph graph) {
        JSONArray jsonPoints = new JSONArray();
        for (Node p : points) {
            jsonPoints.put(graph.nodes().indexOf(p));
        }

        JSONObject json = new JSONObject();
        json.put("weight", weight);
        json.put("path", jsonPoints);
        return json;
    }
}
