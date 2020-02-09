package be.leukspul.data.ecs.component;

import be.leukspul.arc.server.shard.Shard;
import org.json.JSONObject;

public class RefShard extends Component<RefShard> {

    public RefShard(Shard shard) {
        Shard = shard;
    }

    @Override
    public String name() {
        return "Shard";
    }

    public be.leukspul.arc.server.shard.Shard Shard;

    public JSONObject toJSON() throws IllegalAccessException {
        return null;
    }

}
