package be.leukspul.data.ecs.component;

import be.leukspul.arc.library.model.ItemTemplate;

public class Item extends Component<Item> {

    @Override
    public String name() {
        return "Item";
    }

    public ItemTemplate Template;
    public int Amount;
}
