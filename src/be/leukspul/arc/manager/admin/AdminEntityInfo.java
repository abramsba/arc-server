package be.leukspul.arc.manager.admin;

import be.leukspul.arc.server.GameClient;
import be.leukspul.arc.server.packet.server.game.Html;
import be.leukspul.arc.server.shard.Shard;
import be.leukspul.data.ecs.Entity;
import be.leukspul.data.ecs.component.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.script.ScriptException;
import java.io.FileNotFoundException;

public class AdminEntityInfo extends AdminCommand {

    public AdminEntityInfo(String input) {
        super(input);
    }

    @Override
    public void execute(Entity admin) {

        Entity target = admin.get(RefTarget.class).Entity;
        Shard shard = admin.get(RefShard.class).Shard;
        GameClient client = admin.get(RefClient.class).Client;
        try {
            JSONObject json = target.toJSON();

            if (target.has(Avatar.class)) {
                int race = target.get(Avatar.class).Race;
                switch (race) {
                    case 0:
                        json.getJSONObject("Avatar").put("Race", "Human");
                        break;
                    case 1:
                        json.getJSONObject("Avatar").put("Race", "Elf");
                        break;
                    case 2:
                        json.getJSONObject("Avatar").put("Race", "Dark Elf");
                        break;
                    case 3:
                        json.getJSONObject("Avatar").put("Race", "Orc");
                        break;
                    case 4:
                        json.getJSONObject("Avatar").put("Race", "Dwarf");
                }

                int gender = target.get(Avatar.class).Gender;
                switch (gender) {
                    case 0:
                        json.getJSONObject("Avatar").put("Gender", "Male");
                        break;
                    default:
                        json.getJSONObject("Avatar").put("Gender", "Female");
                }
            }

            json.getJSONObject("Position").put("x", (int)target.position().x());
            json.getJSONObject("Position").put("y", (int)target.position().y());
            json.getJSONObject("Position").put("z", (int)target.position().z());
            json.getJSONObject("Direction").put("radians", String.format("%.6f", target.direction().radians()));
            json.getJSONObject("Direction").put("heading", (int)target.direction().heading());

            if (json.has("Task")) {
                int index = 0;
                JSONArray newTasks = new JSONArray();
                JSONArray tasks = json.getJSONArray("Task");
                for (Object task : tasks) {
                    JSONObject newTask = new JSONObject();
                    newTask.put("index", index);
                    newTask.put("instance", task);
                    newTasks.put(newTask);
                    index++;
                }
                json.put("Task", newTasks);
            }

            String html = "";
            if (target.has(Avatar.class)) {
                html = shard.lib().htmTemplates().render("entityInfo", json);
            }
            else if (target.has(Door.class)) {
                html = shard.lib().htmTemplates().render("doorInfo", json);
            }
            Html packet = new Html(shard, admin, html);
            client.send(packet);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
