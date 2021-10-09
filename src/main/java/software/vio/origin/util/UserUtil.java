package software.vio.origin.util;

import software.vio.origin.rank.Rank;
import software.vio.origin.user.UserData;
import org.bukkit.entity.Player;

public final class UserUtil {

    /**
     * Check if a user has a certain minimum rank
     *
     * @param player Player to check
     * @param rank Required rank
     * @return True if the player has the same/higher rank, otherwise false
     */

    public static boolean checkRank(Player player, Rank rank) {
        UserData user = UserData.getByUuid(player.getUniqueId());
        return player.isOp() || user.getRank().getPower() >= rank.getPower();
    }
}
