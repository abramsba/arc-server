package be.leukspul.arc.library.repository;

import be.leukspul.arc.library.model.ItemTemplate;
import org.json.JSONObject;

public class ItemTemplates extends BaseRepository<Integer, ItemTemplate> {
    public ItemTemplates(String filepath) {
        super(filepath);
    }

    @Override
    protected ItemTemplate parse(JSONObject object) {
        ItemTemplate newTemplate = new ItemTemplate();
        newTemplate.Id = object.getInt("id");
        newTemplate.Name = object.getString("name");
        newTemplate.Range = object.getInt("range");
        newTemplate.Speed = object.getInt("speed");
        newTemplate.Weight = object.getInt("weight");
        if (!object.isNull("bodyPart")) {
            newTemplate.BodyPart = object.getString("bodyPart");
        }
        newTemplate.Type = object.getString("type");
        if (!object.isNull("weaponType")) {
            newTemplate.WeaponType = object.getString("weaponType");
        }
        if (!object.isNull("armorType")) {
            newTemplate.ArmorType = object.getString("armorType");
        }
        return newTemplate;
    }

    @Override
    protected Integer key(ItemTemplate data) {
        return data.Id;
    }
}
