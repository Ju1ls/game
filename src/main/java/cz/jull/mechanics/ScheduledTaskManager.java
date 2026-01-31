package cz.jull.mechanics;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskManager {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final List<Task> tasks = new ArrayList<>();
    private boolean started = false;

    public void register(Task task) {
        tasks.add(task);
        if (started) {
            start(task);
        }
    }

    private void start(Task task) {
        scheduler.scheduleAtFixedRate(task.runnable, 0, task.rate, task.rateUnit);
    }

    public void startAll() {
        for (Task task : tasks) {
            start(task);
        }
        started = true;
    }

    public ScheduledFuture<?> registerImmediately(Task task) {
        return scheduler.scheduleAtFixedRate(task.runnable, 0, task.rate, task.rateUnit);
    }

    public void shutdown() throws InterruptedException {
        scheduler.shutdown();
        if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }

    @AllArgsConstructor
    public static class Task {
        private final int rate;
        private final TimeUnit rateUnit;
        private final Runnable runnable;
    }
}
