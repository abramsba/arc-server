package be.leukspul.data.ecs.component;

import be.leukspul.arc.server.GameClient;
import org.json.JSONObject;

public class RefClient extends Component<RefClient> {
    public RefClient(GameClient client) {
        Client = client;
    }
    @Override
    public String name() {
        return "Client";
    }
    public GameClient Client;


    public JSONObject toJSON() throws IllegalAccessException {
        JSONObject object = new JSONObject();
        object.put("clientID", Client.session().playOkID1);
        object.put("clientState", Client.state().toString());
        return object;
    }
}
