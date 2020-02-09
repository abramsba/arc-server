package be.leukspul.data.ecs.component;

import be.leukspul.data.ecs.Entity;
import org.json.JSONObject;

public class RefTarget extends Component<RefTarget> {

    public RefTarget(Entity e) {
        Entity = e;
    }

    @Override
    public String name() {
        return "Target";
    }

    public Entity Entity;

    public JSONObject toJSON() throws IllegalAccessException {
        JSONObject target = new JSONObject();
        target.put("id", Entity.id());
        return target;
    }
}
