package be.leukspul.data.ecs.component;

import org.json.JSONObject;

import java.lang.reflect.Field;

public abstract class Component<T> {

    public abstract String name();
    public T get() {
        return (T) this;
    };
    public Class<T> type() {
        return (Class<T>) this.getClass();
    }
    public Object toJSON() throws IllegalAccessException {
        JSONObject output = new JSONObject();
        Class<? extends Component<T>> javaClass = (Class<? extends Component<T>>) getClass();
        Field[] fields = javaClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getModifiers() != 1) {
                continue;
            }
            Class<?> type = field.getType();
            if (type == int.class || type == Integer.class) {
                output.put(field.getName(), (int)field.get(this));
            }
            else if (type == char.class || type == String.class) {
                output.put(field.getName(), field.get(this));
            }
            else if (type == double.class || type == Double.class) {
                output.put(field.getName(), (double)field.get(this));
            }
            else if (type == boolean.class || type == Boolean.class) {
                output.put(field.getName(), (boolean)field.get(this));
            }
            else {
                output.put(field.getName(), field.get(this).toString());
            }
        }
        return output;
    }
}
