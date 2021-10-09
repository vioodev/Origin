package software.vio.origin.util;

import org.bukkit.Bukkit;

public final class Msg {

    public static final String SERVER_NAME = "Server Network";
    public static final String APPEAL_LINK = "www.servernetwork.com/appeal";
    public static final String PLAYER_NOT_FOUND = CC.RED + "This player could not be found.";

    /**
     * Broadcast a message to everyone - translates '&' color code
     *
     * @param message Message to broadcast
     */

    public static void broadcast(String message) {
        Bukkit.getOnlinePlayers().forEach(online -> online.sendMessage(CC.translate(message)));
        log(message);
    }

    /**
     * Log a message to console - translates '&' color code
     *
     * @param message Message to log
     */

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
    }
}
