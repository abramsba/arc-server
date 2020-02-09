package be.leukspul.data.ecs;

import be.leukspul.data.ecs.component.Component;
import be.leukspul.math.Direction;
import be.leukspul.math.Vector3;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {
    public Entity(int id) {
        this.id = id;
        this.components = new HashMap<>();
        this.position = new Vector3();
        this.direction = new Direction();
    }

    public int id() {
        return id;
    }

    public void add(Component c) {
        components.put(c.type(), c);
    }

    public void add(List<Component> components) {
        for(Component c : components) {
            add(c);
        }
    }

    public <T extends Component> T remove(Class<T> type) {
        return (T)components.remove(type);
    }

    public <T extends Component> T get(Class<T> type) {
        return (T)components.get(type);
    }

    public <T extends Component> boolean has(Class<T> type) {
        return components.containsKey(type);
    }

    public Vector3 position() {
        return position;
    }

    public Direction direction() { return direction; }

    protected int id;
    protected Map<Class<Component>, Component> components;
    protected Vector3 position;
    protected Direction direction;

    public JSONObject toJSON() throws IllegalAccessException {
        JSONObject output = new JSONObject();
        output.put("Id", id());
        output.put("Position", position.toJSON());
        output.put("Direction", direction.toJSON());
        for (Class<Component> key : components.keySet()) {
            Component comp = components.get(key);
            output.put(comp.name(), comp.toJSON());
        }
        return output;
    }
}
