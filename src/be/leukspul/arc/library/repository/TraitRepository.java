package be.leukspul.arc.library.repository;

import be.leukspul.arc.library.model.TraitTemplate;
import org.json.JSONObject;

public class TraitRepository extends BaseRepository<String, TraitTemplate> {
    public TraitRepository(String filepath) {
        super(filepath);
    }

    @Override
    protected TraitTemplate parse(JSONObject object) {
        TraitTemplate template = new TraitTemplate();
        template.Name = object.getString("name");
        template.Description = object.getString("description");
        for (Object next : object.getJSONArray("conflicts")) {
            template.Conflicts.add((String)next);
        }
        return template;
    }

    @Override
    protected String key(TraitTemplate data) {
        return data.Name;
    }
}
