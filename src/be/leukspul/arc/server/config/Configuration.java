package be.leukspul.arc.server.config;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Configuration {

    public Configuration(String filepath) throws FileNotFoundException {
        String jsonString = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
        JSONObject json = new JSONObject(jsonString);
        Mmo = new ConfigMmo(json.getJSONObject("mmocore"));
        Geo = new ConfigGeo(json.getJSONObject("geodata"));
        Login = new ConfigLogin(json.getJSONObject("login"));
        Game = new ConfigGame(json.getJSONObject("game"));
    }

    public final ConfigMmo Mmo;
    public final ConfigGeo Geo;
    public final ConfigLogin Login;
    public final ConfigGame Game;

}
