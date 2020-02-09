package be.leukspul.data.ecs.component;

public class Vitals extends Component<Vitals> {

    public Vitals(int health, int hunger) {
        this.Health = health;
        this.Hunger = hunger;
    }

    @Override
    public String name() {
        return "Vitals";
    }

    public int Health;
    public int Hunger;
    public int Load;
}
