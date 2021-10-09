package software.vio.origin.menu.click;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.inventory.ClickType;

@Getter
@AllArgsConstructor
public class Click {

    // Type of the click
    private final ClickType type;
    // Inventory slot of the click
    private final int slot;
}
