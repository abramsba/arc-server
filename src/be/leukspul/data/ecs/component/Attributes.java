package be.leukspul.data.ecs.component;

public class Attributes extends Component<Attributes> {

    public Attributes(int s, int d, int c, int i, int w, int m) {
        Strength = s;
        Dexterity = d;
        Constitution = c;
        Intelligence = i;
        Wisdom = w;
        Mentality = m;
    }

    @Override
    public String name() {
        return "Attributes";
    }

    public int Strength;
    public int Dexterity;
    public int Constitution;
    public int Intelligence;
    public int Wisdom;
    public int Mentality;
}
