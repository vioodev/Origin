package software.vio.origin.menu.event;

import software.vio.origin.menu.Menu;
import software.vio.origin.menu.click.Click;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class MenuClickEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    // Player clicking the menu
    private final Player player;
    // Menu that is clicked
    private final Menu menu;
    // Click that is registered
    private final Click click;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
