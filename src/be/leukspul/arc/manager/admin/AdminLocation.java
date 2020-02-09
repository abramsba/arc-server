package be.leukspul.arc.manager.admin;

import be.leukspul.data.ecs.Entity;

public class AdminLocation extends AdminCommand {
    public AdminLocation(String input) {
        super(input);
    }

    @Override
    public void execute(Entity admin) {
        System.out.println(admin.position().toJSON().toString());
    }
}
