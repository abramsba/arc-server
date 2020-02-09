package be.leukspul.arc.manager;

import be.leukspul.arc.manager.admin.*;


public class Admin {

    public static AdminCommand Get(String name, String input) throws Exception {
        AdminCommand cmd = null;
        if (name.equals("spawn")) {
            cmd = new AdminSpawn(input);
        }
        else if (name.equals("dist")) {
            cmd = new AdminDist(input);
        }
        else if (name.equals("loc")) {
            cmd = new AdminLocation(input);
        }
        else if (name.equals("teleport")) {
            cmd = new AdminTeleport(input);
        }
        else if (name.equals("info")) {
            cmd = new AdminEntityInfo(input);
        }
        else if (name.equals("html")) {
            cmd = new AdminHtml(input);
        }
        else if (name.equals("wander")) {
            cmd = new AdminWander(input);
        }
        else if (name.equals("traverse")) {
            cmd = new AdminTraverse(input);
        }
        return cmd;
    }

}
