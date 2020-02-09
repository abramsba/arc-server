package be.leukspul.arc.library.repository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class BaseRepository<K, T> {

    public BaseRepository(String filepath) {
        data = new HashMap<>();
        try {
            String jsonString = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                T newEntry = parse(jsonObject);
                if (newEntry != null) {
                    K newKey = key(newEntry);
                    data.put(newKey, newEntry);
                }
            }
        }
        catch (FileNotFoundException error) {
            error.printStackTrace();
        }
    }

    protected abstract T parse(JSONObject object);
    protected abstract K key(T data);

    public T get(K key) {
        return data.get(key);
    }

    public List<T> all() {
        List<T> contents = new ArrayList<>();
        for (K key : data.keySet()) {
            contents.add(data.get(key));
        }
        return contents;
    }

    public T random() {
        K[] keys = (K[])new Object[data.keySet().size()];
        keys = data.keySet().toArray(keys);
        return data.get(keys[random.nextInt(keys.length)]);
    }

    protected Map<K, T> data;
    private static Random random = new Random();


}
