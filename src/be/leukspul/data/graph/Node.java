package be.leukspul.data.graph;

import be.leukspul.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    public int x;
    public int y;
    public int z;
    public int radius;
    public Node() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.radius = 0;
        this.attributes = new HashMap<>();
        this.links = new ArrayList<>();
        this.weights = new ArrayList<>();
    }
    public Node(int x, int y, int z, int radius) {
        this();
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }
    private Map<String, String> attributes;
    private List<Node> links;
    private List<Double> weights;

    public void addLink(Node n) {
        if (!links.contains(n)) {
            this.links.add(n);
            double weight = Math.pow(x - n.x, 2) + Math.pow(y - n.y, 2) + Math.pow(z - n.z, 2);
            this.weights.add(weight);

        }
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    public List<Node> getLinks() {
        return links;
    }

    public List<Double> getWeights() { return weights; }

    public Node getLink(int index) {
        return links.get(index);
    }

    public double getWeight(int index) { return weights.get(index); }

    public double getWeight(Node n) { return weights.get(links.indexOf(n)); }

    public int links() {
        return links.size();
    }

    public Vector3 vec3() {
        return new Vector3(x, y, z);
    }

}
