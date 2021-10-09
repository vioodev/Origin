package software.vio.origin.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import software.vio.origin.menu.button.Button;
import software.vio.origin.menu.button.event.ButtonClickEvent;
import software.vio.origin.menu.click.Click;
import software.vio.origin.menu.event.MenuClickEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu menu = Menu.getByUuid(player.getUniqueId());

        if (event.getClickedInventory() == null) return;
        if (menu == null) return;

        event.setCancelled(true);

        // Call a menu click event if the inventory that is clicked is a menu
        Bukkit.getServer().getPluginManager().callEvent(new MenuClickEvent(player, menu, new Click(event.getClick(), event.getSlot())));
    }

    @EventHandler
    public void onMenuClick(MenuClickEvent event) {
        Player player = event.getPlayer();
        Menu menu = event.getMenu();
        Button button = menu.getButtonBySlot(event.getClick().getSlot());

        if (button == null) return;

        // Call a button click event if the item that is clicked is a button
        Bukkit.getServer().getPluginManager().callEvent(new ButtonClickEvent(player, button, menu, event.getClick()));
    }
}
