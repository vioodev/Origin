package software.vio.origin.menu.button;

import software.vio.origin.menu.button.event.ButtonClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ButtonListener implements Listener {

    @EventHandler
    public void onButtonClick(ButtonClickEvent event) {
        event.getButton().onClick(event);
        // Automatically update menus upon clicking a button
        event.getMenu().update();
    }
}
