package be.leukspul.data.graph;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Compiler {

    public Compiler(int cores) {
        executor = Executors.newScheduledThreadPool(cores);
    }

    public synchronized void addToTime(long ms) {
        this.ms += ms;
    }

    public long ms() {
        return ms;
    }

    public ScheduledFuture<?> compilePath(Graph graph, Paths paths, Node start) {
        return executor.schedule(newFuture(graph, paths, start), 0, TimeUnit.MILLISECONDS);
    }

    public Runnable newFuture(Graph graph, Paths paths, Node start) {
        return () -> {
            long totalTime = 0;
            for (Node current : graph.nodes()) {
                if (current == start) {
                    continue;
                }
                long startTime = System.currentTimeMillis();
                paths.findPaths(start, current, 256);
                long endTime = System.currentTimeMillis();
                //System.out.printf("%s -> %s.\t%dms%n", start.getAttribute("description"), current.getAttribute("description"), (endTime - startTime));
                totalTime += (endTime - startTime);
            }
            System.out.printf("%s done. Duration:\t%dms%n", start.getAttribute("description"), totalTime);
            addToTime(totalTime);
        };
    }

    private long ms;
    public ScheduledExecutorService executor;

}
