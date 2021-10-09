package software.vio.origin.command.impl;

import software.vio.origin.command.OriginCommand;
import software.vio.origin.menu.impl.SettingsMenu;
import org.bukkit.entity.Player;

import java.util.Collections;

public class SettingsCommand extends OriginCommand {

    public SettingsCommand() {
        super("settings", Collections.singletonList("options"), true);
    }

    @Override
    public void execute(Player player, String[] args) {
        new SettingsMenu(player).open();
    }
}
