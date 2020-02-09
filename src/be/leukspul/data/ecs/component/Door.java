package be.leukspul.data.ecs.component;

import be.leukspul.arc.library.model.DoorTemplate;

public class Door extends Component<Door> {

    public Door(DoorTemplate template) {
        Template = template;
    }

    public DoorTemplate Template;
    public boolean Open;

    @Override
    public String name() {
        return "Door";
    }
}
