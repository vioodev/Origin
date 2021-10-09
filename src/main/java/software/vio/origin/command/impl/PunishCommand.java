package software.vio.origin.command.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import software.vio.origin.command.OriginCommand;
import software.vio.origin.punishment.menu.PunishmentMenu;
import software.vio.origin.rank.Rank;
import software.vio.origin.util.CC;
import software.vio.origin.util.Msg;

import java.util.Arrays;

public class PunishCommand extends OriginCommand {

    public PunishCommand() {
        super("punish", Arrays.asList("mute", "kick", "ban", "blacklist"), Rank.TRAINEE);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(CC.RED + "Usage: /punish <player>");
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(Msg.PLAYER_NOT_FOUND);
            return;
        }
        new PunishmentMenu(player, target).open();
    }
}
