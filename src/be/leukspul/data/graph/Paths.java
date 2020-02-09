package be.leukspul.data.graph;

import be.leukspul.data.tree.quad.TreeNode;
import be.leukspul.data.tree.quad.QuadTree;
import be.leukspul.data.Pair;
import net.sf.l2j.gameserver.geoengine.geodata.GeoStructure;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class Paths {

    class Key extends Pair<Node, Node> {
        public Key(Node key, Node value) {
            super(key, value);
        }
        public Key flip() {
            return new Key(getValue(), getKey());
        }
    }

    public Paths(Graph graph) {
        this.graph = graph;
        pathData = new ConcurrentHashMap<>();
    }

    public Path mostEffecientPath(Node start, Node finish) {
        List<Path> paths = pathData.get(new Key(start, finish));
        if (paths == null) {
            paths = pathData.get(new Key(finish, start));
            if (paths == null) {
                return null;
            }
        }
        return paths.get(0);
    }

    public void findPaths(Node start, Node finish, int maxDepth) {
        if (start == finish) {
            return;
        }
        for (int depth = 2; depth < maxDepth; depth++) {
            pather(start, finish, start, null, null, depth);
            List<Path> nextData = pathData.get(new Key(start, finish));
            if (nextData != null && nextData.size() > 0) {
                break;
            }
        }
    }

    private boolean pather(Node start, Node finish, Node current, List<Node> visited, List<Node> path, int maxDepth) {
        if (visited == null) {
            visited = new ArrayList<>();
            path = new ArrayList<>();
        }
        if (current.links() == 0) {
            return false;
        }
        if (path.size() > maxDepth) {
            return false;
        }

        if (current.getLinks().contains(finish)) {
            Path newPathTo = new Path();
            newPathTo.points = new ArrayList<>(path);
            newPathTo.points.add(0, start);
            newPathTo.points.add(finish);
            savePath(newPathTo.points);
            return true;
        }

        for (Node child : current.getLinks()) {
            if (visited.contains(child)) {
                continue;
            }
            visited.add(child);
            path.add(child);
            pather(start, finish, child, new ArrayList<>(visited), new ArrayList<>(path), maxDepth);
            path.remove(child);
        }

        return false;
    }

    private void savePath(List<Node> path) {
        Key keyTo = new Key(path.get(0), path.get(path.size()-1));
        Node previous = null;
        Path pathTo = new Path();
        pathTo.points = new ArrayList<>(path);
        for (int index = 0; index < path.size(); index++) {
            if (previous != null) {
                pathTo.weight += previous.getWeight(path.get(index));
            }
            previous = path.get(index);
        }

        if (pathData.containsKey(keyTo)) {
            List<Path> paths = pathData.get(keyTo);
            paths.add(pathTo);
        }
        else {
            List<Path> newList = Collections.synchronizedList(new ArrayList<>());
            newList.add(pathTo);
            pathData.put(keyTo, newList);
        }
    }

    public JSONArray toJSON() {
        JSONArray output = new JSONArray();
        for(Key key : pathData.keySet()) {
            JSONObject object = new JSONObject();
            object.put("start", graph.nodes().indexOf(key.getKey()));
            object.put("finish", graph.nodes().indexOf(key.getValue()));
            JSONArray jsonPaths = new JSONArray();
            List<Path> paths = pathData.get(key);
            for(Path p : paths) {
                jsonPaths.put(p.toJSON(graph));
            }
            object.put("paths", jsonPaths);
            output.put(object);
        }
        return output;
    }

    public static void main(String [] args) throws IOException {
        String content = null;
        try {
            content = new Scanner(new File("D:\\archive\\arcserver\\dist\\data\\graph\\giran.json")).useDelimiter("\\Z").next();
            //graph = Graph.fromJSON(content);
            QuadTree graphTree = new QuadTree<TreeNode<Node>>(null, null, GeoStructure.WORLD_X_MIN, GeoStructure.WORLD_Y_MIN, GeoStructure.WORLD_WIDTH, GeoStructure.WORLD_HEIGHT, 512, (Class) ArrayList.class);
            Graph graph = Graph.loadIntoQuadTree(content, graphTree);


            Paths path = new Paths(graph);

            Node start = graph.nodes().get(0);
            Node finish = graph.nodes().get(100);

            Compiler pcomp = new Compiler(8);
            List<Future> futures = new ArrayList<>();
            //futures.add(pcomp.compilePath(graph, path, start));

            for (Node current : graph.nodes()) {
                futures.add(pcomp.compilePath(graph, path, current));
            }

            boolean loop = true;
            int doneCount;
            while (loop) {
                doneCount = 0;
                for (Future f : futures) {
                    doneCount += f.isDone() ? 1 : 0;
                }

                if (doneCount == futures.size()) {
                    loop = false;
                }
            }
            System.out.println("Ok done. " + pcomp.ms() + "ms");
            pcomp.executor.shutdown();

            Path p = path.mostEffecientPath(start, finish);
            Files.write(java.nio.file.Paths.get("D:\\archive\\arcserver\\dist\\data\\graph\\giran_compiled.json"), path.toJSON().toString().getBytes());

            //System.out.println(p.toJSON(graph).toString());
            int ok = 0;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Map<Key, List<Path>> pathData;
    private Graph graph;

}
