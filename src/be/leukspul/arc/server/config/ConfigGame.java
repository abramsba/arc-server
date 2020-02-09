package be.leukspul.arc.server.config;

import org.json.JSONObject;

public class ConfigGame {

    public ConfigGame(JSONObject json) {
        Bind = json.getString("bind");
        Hostname = json.getString("hostname");
        LocalHostname = json.getString("localHostname");
        Port = json.getInt("port");
    }
    public final String Bind;
    public final String Hostname;
    public final String LocalHostname;
    public final int Port;
}
