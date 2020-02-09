package be.leukspul.arc.server.config;

import be.leukspul.math.Vector3;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;
import java.util.List;

public class ConfigGeo {

    public ConfigGeo(JSONObject json) {
        MaxIterations = json.getInt("maxIterations");
        BaseWeight = json.getInt("baseWeight");
        DiagonalWeight = json.getInt("diagonalWeight");
        ObstacleMultiplier = json.getInt("obstacleMultiplier");
        HeuristicWeight = json.getInt("heuristicWeight");
        Buffers = json.getString("buffers");
        Folder = json.getString("folder");
        CharacterHeight = json.getInt("characterHeight");
        MaxObstacleHeight = json.getInt("maxObstacleHeight");
        Regions = new ArrayList<>();
        for(Object next : json.getJSONArray("regions")) {
            String buf = (String)next;
            String [] splits = buf.split("_");
            Regions.add(new Vector3(Double.parseDouble(splits[0]), Double.parseDouble(splits[1]), 0));
        }
    }

    public final String Folder;
    public final int MaxIterations;
    public final int BaseWeight;
    public final int DiagonalWeight;
    public final int ObstacleMultiplier;
    public final int HeuristicWeight;
    public final int CharacterHeight;
    public final int MaxObstacleHeight;
    public final String Buffers;
    public final List<Vector3> Regions;
}
