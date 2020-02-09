package be.leukspul.arc.server.config;

import org.json.JSONObject;

import java.util.HashSet;

public class ConfigLogin {

    public ConfigLogin(JSONObject json) {
        Hostname = json.getString("hostname");
        Port = json.getInt("port");
    }

    public final String Hostname;
    public final int Port;
}
