package be.leukspul.data.ecs.component;

import be.leukspul.arc.manager.entity.task.BaseTask;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RefTask extends Component<RefTask> {
    @Override
    public String name() {
        return "Task";
    }

    public Consumer<List<BaseTask>> List;

    public JSONArray toJSON() {
        JSONArray array = new JSONArray();
        if (List != null) {
            List<BaseTask> tasks = new ArrayList<>();
            List.accept(tasks);
            for (BaseTask task : tasks) {
                String [] jclassName = task.toString().split("\\.");
                array.put(jclassName[jclassName.length-1]);
            }
        }
        return array;
    }

}
