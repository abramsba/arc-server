package be.leukspul.arc.calc;

import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.Avatar;

public class Calculator {

    public static double realSpeed(Entity e) {
        Avatar avatar = e.get(Avatar.class);
        if (avatar.Walking) {
            return avatar.WalkSpeed + avatar.BonusSpeed;
        }
        return avatar.RunSpeed + avatar.BonusSpeed;
    }

    public static double speed(Entity e) {
        Avatar avatar = e.get(Avatar.class);
        if (avatar.Walking) {
            return avatar.WalkSpeed;
        }
        return avatar.RunSpeed;
    }

    public static double movementMultiplier(Entity e) {
        Avatar avatar = e.get(Avatar.class);
        if (avatar.Walking) {
            return realSpeed(e) / avatar.WalkSpeed;
        }
        return realSpeed(e) / avatar.RunSpeed;
    }



}
