package be.leukspul.data.ecs.component;

import be.leukspul.data.ecs.Entity;

public class ItemOwner extends Component<ItemOwner> {

    public ItemOwner(Entity owner) {
        entity = owner;
    }

    @Override
    public String name() {
        return "ItemOwner";
    }
    public Entity entity;
}
