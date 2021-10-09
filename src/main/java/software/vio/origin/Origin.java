package software.vio.origin;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import software.vio.origin.command.impl.PunishCommand;
import software.vio.origin.command.impl.SettingsCommand;
import software.vio.origin.command.impl.StaffModeCommand;
import software.vio.origin.database.mongo.MongoService;
import software.vio.origin.database.redis.RedisExample;
import software.vio.origin.database.redis.RedisService;
import software.vio.origin.menu.MenuListener;
import software.vio.origin.menu.button.ButtonListener;
import software.vio.origin.user.UserData;
import software.vio.origin.user.UserListener;
import software.vio.origin.user.UserManager;
import software.vio.origin.util.CC;
import software.vio.origin.util.Msg;
import software.vio.origin.util.ServerUtil;

import java.util.stream.Stream;

@Getter
public class Origin extends JavaPlugin {

    @Getter
    private static Origin instance;

    private UserManager userManager;

    private MongoService mongoService;
    private RedisService redisService;

    @Override
    public void onEnable() {
        instance = this;

        this.loadCommands();
        this.loadConfig();
        this.loadListeners();
        this.loadManagers();
        this.loadServices();

        new RedisExample(this.redisService);

        Msg.log(CC.GOLD + "Origin " + CC.YELLOW + "has been enabled.");
    }

    @Override
    public void onDisable() {
        UserData.getUsers().values().forEach(UserData::saveData);

        this.mongoService.disconnect();
        this.redisService.disconnect();
        this.saveConfig();

        instance = null;

        Msg.log(CC.GOLD + "Origin " + CC.YELLOW + "has been disabled.");
    }

    private void loadCommands() {
        Stream.of(
                new PunishCommand(),
                new SettingsCommand(),
                new StaffModeCommand()
        ).forEach(command -> ServerUtil.registerCommand(command, this));
    }

    private void loadConfig() {
        FileConfiguration config = this.getConfig();

        config.addDefault("MONGO.HOST", "127.0.0.0");
        config.addDefault("MONGO.PORT", 27017);
        config.addDefault("MONGO.DATABASE", "origin");
        config.addDefault("MONGO.USER", "root");
        config.addDefault("MONGO.PASSWORD", "");

        config.addDefault("REDIS.HOST", "127.0.0.0");
        config.addDefault("REDIS.PORT", 6379);
        config.addDefault("REDIS.CHANNEL", "origin");
        config.addDefault("REDIS.AUTHENTICATION", false);
        config.addDefault("REDIS.PASSWORD", "");

        config.options().copyDefaults(true);
        this.saveConfig();
    }

    private void loadListeners() {
        Stream.of(
                new ButtonListener(),
                new MenuListener(),
                new UserListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void loadManagers() {
        this.userManager = new UserManager();
    }

    private void loadServices() {
        this.mongoService = new MongoService(this);
        this.redisService = new RedisService(this);
    }
}
