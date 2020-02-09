package be.leukspul.data.graph;

import be.leukspul.data.tree.quad.TreeNode;
import be.leukspul.data.tree.quad.QuadTree;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    public Node root;
    public List<Node> list;
    public int lowx;
    public int lowy;
    public int highx;
    public int highy;

    public Graph() {
        list = new ArrayList<>();
        lowx = Integer.MAX_VALUE;
        lowy = Integer.MAX_VALUE;
        highx = Integer.MIN_VALUE;
        highy = Integer.MIN_VALUE;
    }

    public int getWidth() {
        return highx - lowx;
    }

    public int getHeight() {
        return highy - highy;
    }

    public Node findNearestNode(int x, int y, int z) {
        Node found = null;
        double lowestDistance = Double.MAX_VALUE;
        for(Node n : list) {
            double x2 = Math.pow(x - n.x, 2);
            double y2 = Math.pow(y - n.y, 2);
            double dist = Math.sqrt(x2 + y2);
            if (dist < lowestDistance) {
                found = n;
                lowestDistance = dist;
            }
        }
        return found;
    }

    public List<Node> nodes() {
        return list;
    }

    public static Graph loadIntoQuadTree(String input, QuadTree<TreeNode<Node>> tree) {
        Graph g = new Graph();
        List<Node> createdNodes = new ArrayList<>();
        JSONObject json = new JSONObject(input);
        JSONArray nodes = json.getJSONArray("nodes");
        JSONArray links = json.getJSONArray("links");
        for(Object node : nodes) {
            JSONObject nodeJson = (JSONObject)node;
            Node newNode = new Node();
            newNode.x = nodeJson.getInt("x");
            if (newNode.x < g.lowx) {
                g.lowx = newNode.x;
            }
            else if (newNode.x > g.highx) {
                g.highx = newNode.x;
            }

            newNode.y = nodeJson.getInt("y");
            if (newNode.y < g.lowy) {
                g.lowy = newNode.y;
            }
            else if (newNode.y > g.highy) {
                g.highy = newNode.y;
            }

            newNode.z = nodeJson.getInt("z");
            newNode.radius = nodeJson.getInt("radius");
            JSONObject attributes = nodeJson.getJSONObject("attributes");
            for(String key : attributes.keySet()) {
                newNode.addAttribute(key, attributes.getString(key));
            }
            createdNodes.add(newNode);
            TreeNode<Node> newTreeNode = new TreeNode<>(newNode, newNode.x, newNode.y);
            tree.add(newTreeNode);
        }
        for(Object link : links) {
            JSONObject linkJson = (JSONObject)link;
            int a = linkJson.getInt("a");
            int b = linkJson.getInt("b");
            Node nodeA = createdNodes.get(a);
            Node nodeB = createdNodes.get(b);
            nodeA.addLink(nodeB);
            nodeB.addLink(nodeA);
        }
        if (nodes.length() > 0) {
            g.root = createdNodes.get(0);
        }
        g.list = createdNodes;
        return g;
    }

    public static Graph fromJSON(String input) {
        Graph g = new Graph();
        List<Node> createdNodes = new ArrayList<>();
        JSONObject json = new JSONObject(input);
        JSONArray nodes = json.getJSONArray("nodes");
        JSONArray links = json.getJSONArray("links");
        for(Object node : nodes) {
            JSONObject nodeJson = (JSONObject)node;
            Node newNode = new Node();
            newNode.x = nodeJson.getInt("x");
            if (newNode.x < g.lowx) {
                g.lowx = newNode.x;
            }
            else if (newNode.x > g.highx) {
                g.highx = newNode.x;
            }

            newNode.y = nodeJson.getInt("y");
            if (newNode.y < g.lowy) {
                g.lowy = newNode.y;
            }
            else if (newNode.y > g.highy) {
                g.highy = newNode.y;
            }

            newNode.z = nodeJson.getInt("z");
            newNode.radius = nodeJson.getInt("radius");
            JSONObject attributes = nodeJson.getJSONObject("attributes");
            for(String key : attributes.keySet()) {
                newNode.addAttribute(key, attributes.getString(key));
            }
            createdNodes.add(newNode);
        }
        for(Object link : links) {
            JSONObject linkJson = (JSONObject)link;
            int a = linkJson.getInt("a");
            int b = linkJson.getInt("b");
            Node nodeA = createdNodes.get(a);
            Node nodeB = createdNodes.get(b);
            nodeA.addLink(nodeB);
            nodeB.addLink(nodeA);
        }
        if (nodes.length() > 0) {
            g.root = createdNodes.get(0);
        }
        g.list = createdNodes;
        return g;
    }
}
