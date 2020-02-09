package be.leukspul.data.ecs.component;

import be.leukspul.arc.library.model.SkillTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Skills extends Component<Skills> {
    public Skills() {
        Skills = new HashSet<>();
    }
    @Override
    public String name() {
        return "Skills";
    }
    public Set<SkillTemplate> Skills;

    @Override
    public JSONArray toJSON() throws IllegalAccessException {
        JSONArray skills = new JSONArray();
        for (SkillTemplate temp : Skills) {
            JSONObject skill = new JSONObject();
            skill.put("Level", temp.Level);
            skill.put("Name", temp.Name);
            skills.put(skill);
        }
        return skills;
    }
}
