package be.leukspul.arc.library.repository;

import be.leukspul.arc.library.model.DoorTemplate;
import org.json.JSONObject;

public class DoorTemplates extends BaseRepository<Integer, DoorTemplate> {
    public DoorTemplates(String filepath) {
        super(filepath);
    }

    @Override
    protected DoorTemplate parse(JSONObject object) {
        DoorTemplate newTemplate = new DoorTemplate();
        newTemplate.Id = object.getInt("id");
        newTemplate.Type = object.getString("type");
        newTemplate.Name = object.getString("name");
        newTemplate.x = object.getInt("x");
        newTemplate.y = object.getInt("y");
        newTemplate.z = object.getInt("z");
        return newTemplate;
    }

    @Override
    protected Integer key(DoorTemplate data) {
        return data.Id;
    }
}
