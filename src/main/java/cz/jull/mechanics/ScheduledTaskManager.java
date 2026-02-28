package cz.jull.mechanics;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Manages the execution of background tasks and game mechanics that occur over time.
 *
 * @author Julie Šefl
 */
public class ScheduledTaskManager {
    public final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final List<Task> tasks = new ArrayList<>();
    private boolean started = false;

    /**
     * Registers a task to be run by the manager.
     * @param task The definition of the task (logic and frequency).
     */
    public void register(Task task) {
        tasks.add(task);
        if (started) {
            start(task);
        }
    }

    /**
     * Internal helper to schedule a specific task on the executor.
     * @param task The task to schedule.
     */
    private void start(Task task) {
        scheduler.scheduleAtFixedRate(task.runnable, 0, task.rate, task.rateUnit);
    }

    /**
     * Ignites all registered tasks.
     */
    public void startAll() {
        for (Task task : tasks) {
            start(task);
        }
        started = true;
    }

    /**
     * Registers and starts a task immediately, returning its future for control.
     * @param task The task to execute.
     * @return A {@link ScheduledFuture} representing pending completion of the task,
     * allow for cancellation via {@code future.cancel(false)}.
     */
    public ScheduledFuture<?> registerImmediately(Task task) {
        return scheduler.scheduleAtFixedRate(task.runnable, 0, task.rate, task.rateUnit);
    }

    /**
     * Gracefully terminates all background threads.
     * @throws InterruptedException if the shutdown wait is interrupted.
     */
    public void shutdown() throws InterruptedException {
        scheduler.shutdown();
        if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
    }

    /**
     * A simple data carrier for defining a scheduled task.
     */
    @AllArgsConstructor
    public static class Task {
        private final int rate;
        private final TimeUnit rateUnit;
        private final Runnable runnable;
    }
}
