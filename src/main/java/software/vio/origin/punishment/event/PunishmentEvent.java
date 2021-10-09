package software.vio.origin.punishment.event;

import software.vio.origin.punishment.Punishment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class PunishmentEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    // Player who is punished
    private final Player target;
    // Player who executes the punishment
    private final Player sender;
    // Punishment
    private final Punishment punishment;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
