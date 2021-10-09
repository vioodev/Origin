package software.vio.origin.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import software.vio.origin.command.OriginCommand;

import java.lang.reflect.Field;

public final class ServerUtil {

    /**
     * Register a command to the Bukkit command map
     *
     * @param command Command to register
     * @param plugin  Plugin the command belongs to
     */

    public static void registerCommand(OriginCommand command, Plugin plugin) {
        Field field;

        try {
            field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
            commandMap.register(plugin.getName(), command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
