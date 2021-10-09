package software.vio.origin.user;

import software.vio.origin.util.CC;
import org.bukkit.entity.Player;

public class UserManager {

    /**
     * Toggle a user's staff mode
     *
     * @param player Player to toggle
     */

    public void toggleStaffMode(Player player) {
        UserData user = UserData.getByUuid(player.getUniqueId());
        user.setStaffMode(!user.isStaffMode());

        // TODO: Give items

        player.sendMessage(CC.YELLOW + "You are " + CC.GOLD + (user.isStaffMode() ? "now " : "no longer ") + CC.YELLOW + "in staff mode.");
    }
}
