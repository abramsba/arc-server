package be.leukspul.arc.library.repository;

import be.leukspul.arc.library.model.SkillTemplate;
import be.leukspul.data.Pair;
import org.json.JSONObject;

public class SkillTemplates extends BaseRepository<Pair<Integer, Integer>, SkillTemplate> {

    public SkillTemplates(String filepath) {
        super(filepath);
    }

    @Override
    protected SkillTemplate parse(JSONObject object) {
        int skillId = object.getInt("id");
        String name = object.getString("name");
        String target = object.getString("target");
        String operateType = object.getString("operateType");
        int hitTime = !object.isNull("hitTime") ? object.getInt("hitTime") : 0;
        int coolTime = !object.isNull("coolTime") ? object.getInt("coolTime") : 0;
        for (int i = 0; i < object.getInt("levels"); i++) {
            SkillTemplate tpl = new SkillTemplate();
            tpl.SkillId = skillId;
            tpl.Level = (i + 1);
            tpl.Name = name;
            tpl.Target = target;
            tpl.Type = operateType;
            tpl.HitTime = hitTime;
            tpl.CoolTime = coolTime;
            data.put(key(tpl), tpl);
        }
        return null;
    }

    @Override
    protected Pair<Integer, Integer> key(SkillTemplate data) {
        return new Pair<>(data.SkillId, data.Level);
    }


}
