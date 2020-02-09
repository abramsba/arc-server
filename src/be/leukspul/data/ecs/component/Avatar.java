package be.leukspul.data.ecs.component;

import be.leukspul.arc.library.model.NpcTemplate;
import be.leukspul.arc.library.model.PcTemplate;

public class Avatar extends Component<Avatar> {

    public Avatar(PcTemplate template, int gender, int face, int hair, int color) {
        Race = template.RaceId;
        Class = template.ClassId;
        Gender = gender;
        Face = face;
        Hair = hair;
        Color = color;
        HeightMale = template.HeightMale;
        HeightFemale = template.HeightFemale;
        RadiusMale = template.RadiusMale;
        RadiusFemale = template.RadiusFemale;
        RunSpeed = template.RunSpeed;
        WalkSpeed = template.WalkSpeed;
        SwimSpeed = template.SwimSpeed;
    }

    public Avatar(NpcTemplate template) {
        IsNpc = true;
        NpcId = template.NpcId;
        TemplateId = template.TemplateId;
        Height = template.Height;
        Radius = template.Radius;
        AttackSpeed = template.AttackSpeed;
        RunSpeed = template.RunSpeed;
        WalkSpeed = template.WalkSpeed;
        SwimSpeed = template.WalkSpeed;
    }

    public int Race;
    public int Class;
    public int Gender;
    public int Face;
    public int Hair;
    public int Color;
    public double HeightMale;
    public double HeightFemale;
    public double RadiusMale;
    public double RadiusFemale;
    public boolean Walking;
    public boolean Sitting;
    public boolean Teleporting;
    public int RunSpeed;
    public int WalkSpeed;
    public int SwimSpeed;
    public int BonusSpeed;
    public String Name;
    public String Title;
    public boolean IsNpc;
    public int NpcId;
    public int TemplateId;
    public double Height;
    public double Radius;
    public int AttackSpeed;

    @Override
    public String name() {
        return "Avatar";
    }
}
