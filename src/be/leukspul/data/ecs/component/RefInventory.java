package be.leukspul.data.ecs.component;


import be.leukspul.arc.manager.inventory.EntityInventory;

public class RefInventory extends Component<RefInventory> {
    public RefInventory(EntityInventory inventory) {
        Inventory = inventory;
    }
    @Override
    public String name() {
        return "Inventory";
    }
    public EntityInventory Inventory;
}
