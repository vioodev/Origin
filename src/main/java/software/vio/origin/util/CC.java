package software.vio.origin.util;

import org.bukkit.ChatColor;

public final class CC {

    /**
     * Translate '&' color codes
     *
     * @param message Message to translate
     * @return Translated message
     */

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    // Bukkit ChatColor colors
    public static final String BLACK = ChatColor.BLACK.toString(); // §0
    public static final String DARK_BLUE = ChatColor.DARK_BLUE.toString(); // §1
    public static final String DARK_GREEN = ChatColor.DARK_GREEN.toString(); // §2
    public static final String DARK_AQUA = ChatColor.DARK_AQUA.toString(); // §3
    public static final String DARK_RED = ChatColor.DARK_RED.toString(); // §4
    public static final String DARK_PURPLE = ChatColor.DARK_PURPLE.toString(); // §5
    public static final String GOLD = ChatColor.GOLD.toString(); // §6
    public static final String GRAY = ChatColor.GRAY.toString(); // §7
    public static final String DARK_GRAY = ChatColor.DARK_GRAY.toString(); // §8
    public static final String BLUE = ChatColor.BLUE.toString(); // §9
    public static final String GREEN = ChatColor.GREEN.toString(); // §a
    public static final String AQUA = ChatColor.AQUA.toString(); // §b
    public static final String RED = ChatColor.RED.toString(); // §c
    public static final String LIGHT_PURPLE = ChatColor.LIGHT_PURPLE.toString(); // §d
    public static final String YELLOW = ChatColor.YELLOW.toString(); // §e
    public static final String WHITE = ChatColor.WHITE.toString(); // §f

    // Bukkit ChatColor formatting
    public static final String OBFUSCATED = ChatColor.MAGIC.toString(); // §k
    public static final String BOLD = ChatColor.BOLD.toString(); // §l
    public static final String STRIKETHROUGH = ChatColor.STRIKETHROUGH.toString(); // §m
    public static final String UNDERLINE = ChatColor.UNDERLINE.toString(); // §n
    public static final String ITALIC = ChatColor.ITALIC.toString(); // §o
    public static final String RESET = ChatColor.RESET.toString(); // §r
}
