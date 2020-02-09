package be.leukspul.arc.manager.admin;

import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.RefTarget;

public class AdminDist extends AdminCommand {
    public AdminDist(String input) {
        super(input);
    }

    @Override
    public void execute(Entity admin) {
        if (admin.has(RefTarget.class)) {
            Entity target = admin.get(RefTarget.class).Entity;
            double dist = admin.position().dist2(target.position());
            System.out.println("Distance is: "+dist);
        }
    }
}
