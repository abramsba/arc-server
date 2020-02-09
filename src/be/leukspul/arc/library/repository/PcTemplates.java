package be.leukspul.arc.library.repository;

import be.leukspul.arc.library.model.PcTemplate;
import be.leukspul.data.Pair;
import org.json.JSONObject;


public class PcTemplates extends BaseRepository<Pair<Integer, Integer>, PcTemplate> {


    public PcTemplates(String filepath) {
        super(filepath);
    }

    @Override
    protected PcTemplate parse(JSONObject object) {
        PcTemplate newTemplate = new PcTemplate();
        newTemplate.ClassId = object.getInt("classId");
        newTemplate.RaceId = object.getInt("raceId");
        newTemplate.RadiusMale = object.getDouble("radiusMale");
        newTemplate.HeightMale = object.getDouble("heightMale");
        newTemplate.RadiusFemale = object.getDouble("radiusFemale");
        newTemplate.HeightFemale = object.getDouble("heightFemale");
        newTemplate.RunSpeed = object.getInt("runSpeed");
        newTemplate.WalkSpeed = object.getInt("walkSpeed");
        newTemplate.SwimSpeed = object.getInt("swimSpeed");
        return newTemplate;
    }

    @Override
    protected Pair<Integer, Integer> key(PcTemplate data) {
        return new Pair<>(data.ClassId, data.RaceId);
    }


}
