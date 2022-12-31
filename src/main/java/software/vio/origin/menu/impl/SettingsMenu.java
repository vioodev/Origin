package software.vio.origin.menu.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import software.vio.origin.menu.Menu;
import software.vio.origin.menu.button.Button;
import software.vio.origin.menu.button.event.ButtonClickEvent;
import software.vio.origin.rank.Rank;
import software.vio.origin.util.CC;
import software.vio.origin.util.UserUtil;
import software.vio.origin.util.item.ItemBuilder;

public class SettingsMenu extends Menu {

    public SettingsMenu(Player viewer) {
        super(viewer, "Settings", 1);
    }

    @Override
    protected void setup() {
        if (!UserUtil.checkRank(this.getViewer(), Rank.TRAINEE)) return;

        // Staff settings
        this.set(4, new Button(new ItemBuilder(Material.NETHER_STAR)
                .setName(CC.GOLD + "Staff Mode")
                .setLore("",
                        CC.GRAY + "Improves your staffing experience.",
                        "",
                        CC.YELLOW + "Click to toggle!")
                .get()) {
            @Override
            public void onClick(ButtonClickEvent event) {
                event.getPlayer().performCommand("staffmode");
            }
        });
    }
}
