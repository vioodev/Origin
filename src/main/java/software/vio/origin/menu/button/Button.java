package software.vio.origin.menu.button;

import software.vio.origin.menu.button.event.ButtonClickEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public abstract class Button {

    // Item stack of the button
    private final ItemStack item;

    // On implementation, everything that should happen upon clicking the button should be stated here
    public abstract void onClick(ButtonClickEvent event);
}
