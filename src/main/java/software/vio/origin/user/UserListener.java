package software.vio.origin.user;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import software.vio.origin.punishment.Punishment;
import software.vio.origin.punishment.PunishmentType;
import software.vio.origin.util.CC;
import software.vio.origin.util.Clickable;
import software.vio.origin.util.Msg;
import software.vio.origin.util.TimeUtil;

import java.util.Comparator;

public class UserListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        UserData user = UserData.getByUuid(player.getUniqueId());

        if (user.getActivePunishments(PunishmentType.BAN, PunishmentType.BLACKLIST).count() > 0) {
            Punishment punishment = user.getActivePunishments(PunishmentType.BAN, PunishmentType.BLACKLIST).max(Comparator.comparingInt(Punishment::getPower)).get();

            String kickScreen = CC.RED + "You are " + punishment.getType().getMessage() + " from " + Msg.SERVER_NAME + ".\n" +
                    CC.RED + "Reason: " + punishment.getReason() + "\n" +
                    (punishment.getType() == PunishmentType.BAN ? CC.RED + "Expires in: " + TimeUtil.getPrettyTime(punishment.getDuration()) + "\n" : "") +
                    "\n" +
                    CC.RED + (punishment.getType() == PunishmentType.BLACKLIST ? "You may not appeal this type of punishment." : punishment.getType() == PunishmentType.BAN ? "You can appeal this punishment at " + Msg.APPEAL_LINK + "." : "");

            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickScreen);
        }
    }

    // TODO: Call a custom chat event here

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UserData user = UserData.getByUuid(player.getUniqueId());

        event.setCancelled(true);

        if (user.getActivePunishments(PunishmentType.MUTE).findAny().isPresent()) {
            Punishment punishment = user.getActivePunishments(PunishmentType.MUTE).max(Comparator.comparingInt(Punishment::getPower)).get();
            player.sendMessage(CC.RED + "You are currently muted for " + punishment.getReason() + " for another " + TimeUtil.getPrettyTimeDiff(System.currentTimeMillis(), punishment.getIssuedWhen() + punishment.getDuration()) + ".");
            return;
        }
        Clickable clickable = new Clickable(
                user.getPrettyName() + CC.GRAY + ": " + CC.WHITE + event.getMessage(),
                CC.YELLOW + "Click to message!",
                ClickEvent.Action.SUGGEST_COMMAND,
                "/msg " + event.getPlayer().getName() + " "
        );

        Bukkit.getOnlinePlayers().stream()
                .filter(online -> !online.getUniqueId().equals(player.getUniqueId()))
                .forEach(clickable::send);
        // Only send the message since the user cannot message themself
        player.sendMessage(clickable.getMessage());
    }
}
