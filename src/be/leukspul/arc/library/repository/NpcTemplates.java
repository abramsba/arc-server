package be.leukspul.arc.library.repository;

import be.leukspul.arc.library.model.NpcTemplate;
import org.json.JSONObject;

public class NpcTemplates extends BaseRepository<Integer, NpcTemplate> {
    public NpcTemplates(String filepath) {
        super(filepath);
    }

    @Override
    protected NpcTemplate parse(JSONObject object) {
        NpcTemplate newTemplate = new NpcTemplate();
        newTemplate.NpcId = object.getInt("npcId");
        newTemplate.Height = object.getDouble("height");
        newTemplate.Radius = object.getDouble("radius");
        boolean has = object.has("templateId") && !object.isNull("templateId");
        newTemplate.TemplateId = has ? object.getInt("templateId") : -1;
        newTemplate.AttackSpeed = object.getInt("attackSpeed");
        newTemplate.RunSpeed = object.getInt("runSpeed");
        newTemplate.WalkSpeed = object.getInt("walkSpeed");
        newTemplate.Name = object.getString("name");
        return newTemplate;
    }

    @Override
    protected Integer key(NpcTemplate data) {
        return data.NpcId;
    }
}
