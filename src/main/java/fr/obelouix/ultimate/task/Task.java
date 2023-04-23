package fr.obelouix.ultimate.task;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.util.Consumer;

public record Task(Object wrapped, Consumer<Object> canceller) {
    public static Task wrapFolia(final ScheduledTask scheduledTask) {
        return new Task(scheduledTask, task -> ((ScheduledTask) task).cancel());
    }

    public void cancel() {
        this.canceller.accept(this.wrapped);
    }
}
