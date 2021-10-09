package software.vio.origin.util.task;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import software.vio.origin.Origin;

@Getter
public abstract class Timer {

    private int seconds;
    private boolean expired;

    /**
     * Create a new timer
     *
     * @param seconds Starting seconds to decrement
     */

    public Timer(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Start the timer
     */

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (seconds == 0) {
                    expired = true;
                    onExpire();
                    this.cancel();
                }
                seconds--;
                onUpdate();
            }
        }.runTaskTimer(Origin.getInstance(), 0L, 20L);
    }

    /**
     * On implementation, everything that should happen when the timer updates should be stated here
     */

    protected abstract void onUpdate();

    /**
     * On implementation, everything that should happen when the timer expires should be stated here
     */

    protected abstract void onExpire();
}
