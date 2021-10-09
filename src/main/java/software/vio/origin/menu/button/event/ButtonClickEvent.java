package software.vio.origin.menu.button.event;

import software.vio.origin.menu.Menu;
import software.vio.origin.menu.button.Button;
import software.vio.origin.menu.click.Click;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class ButtonClickEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    // Player clicking on the button
    private final Player player;
    // Button that is clicked on
    private final Button button;
    // Menu that contains the button
    private final Menu menu;
    // Click that is registered
    private final Click click;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
