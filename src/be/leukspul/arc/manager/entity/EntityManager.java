package be.leukspul.arc.manager.entity;

import be.leukspul.arc.manager.entity.task.BaseTask;
import be.leukspul.data.ecs.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class EntityManager {
    public EntityManager(int poolSize) {
        executor = Executors.newScheduledThreadPool(poolSize);
        tasks = new ConcurrentHashMap<>();
        futures = new ConcurrentHashMap<>();
    }
    public void add(BaseTask task) {
        if (tasks.containsKey(task.entity())) {
            tasks.get(task.entity()).tail().next = task;
        }
        else {
            execute(task);
        }
    }
    public void stop(Entity entity) {
        BaseTask task = tasks.get(entity);
        if (task != null) {
            Future future = futures.get(entity);
            task.onCancel();
            task.next = null;
            future.cancel(true);
        }
    }
    public void cancel(Entity entity, BaseTask task) {
        BaseTask topTask = tasks.get(entity);
        if (topTask == task) {
            futures.get(entity).cancel(true);
            topTask.onCancel();
            futures.remove(entity);
            tasks.remove(entity);
        }
        else if (topTask != null) {
            task.stop = true;
        }
    }
    public List<BaseTask> entityTasks(Entity entity) {
        List<BaseTask> eTasks = new ArrayList<>();
        BaseTask topTask = tasks.get(entity);
        if (topTask != null) {
            eTasks.add(topTask);
            BaseTask curr = topTask.next;
            while (curr != null) {
                if (!curr.stop)
                    eTasks.add(curr);
                curr = curr.next;
            }
        }
        return eTasks;
    }
    private void execute(BaseTask task) {
        Runnable runTask = () -> {
            if (!task.stop) {
                tasks.put(task.entity(), task);
                task.onStart();
                task.execute();
            }
            if (task.next != null) {
                execute(task.next);
            }
            else {
                tasks.remove(task.entity());
            }

        };
        futures.put(task.entity(), executor.schedule(runTask, 0, TimeUnit.MILLISECONDS));
    }
    private Map<Entity, BaseTask> tasks;
    private Map<Entity, Future> futures;
    private ScheduledExecutorService executor;
}
