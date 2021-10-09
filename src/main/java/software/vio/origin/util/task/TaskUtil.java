package software.vio.origin.util.task;

import software.vio.origin.Origin;
import org.bukkit.Bukkit;

public final class TaskUtil {

    /**
     * Start a new Bukkit task
     *
     * @param callable What to run
     * @param delay Delay on start
     * @param interval Interval of call
     */

    public static void runTimer(Callable callable, long delay, long interval) {
        Bukkit.getServer().getScheduler().runTaskTimer(Origin.getInstance(), callable::call, delay, interval);
    }

    /**
     * Run a task async
     *
     * @param callable What to run
     */

    public static void runAsync(Callable callable) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Origin.getInstance(), callable::call);
    }
}
