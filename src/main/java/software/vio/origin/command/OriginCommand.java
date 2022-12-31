package software.vio.origin.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import software.vio.origin.rank.Rank;
import software.vio.origin.util.CC;
import software.vio.origin.util.UserUtil;

import java.util.List;

public abstract class OriginCommand extends BukkitCommand {

    private boolean playersOnly;
    private Rank rank;

    public OriginCommand(String name) {
        super(name);

        this.rank = Rank.DEFAULT;
        this.playersOnly = false;
    }

    public OriginCommand(String name, List<String> aliases) {
        this(name);

        this.setAliases(aliases);
        this.rank = Rank.DEFAULT;
        this.playersOnly = false;
    }

    public OriginCommand(String name, boolean playersOnly) {
        this(name);

        this.rank = Rank.DEFAULT;
        this.playersOnly = playersOnly;
    }

    public OriginCommand(String name, Rank rank) {
        this(name);

        this.rank = rank;
        this.playersOnly = true;
    }

    public OriginCommand(String name, List<String> aliases, boolean playersOnly) {
        this(name);

        this.setAliases(aliases);
        this.rank = Rank.DEFAULT;
        this.playersOnly = playersOnly;
    }

    public OriginCommand(String name, List<String> aliases, Rank rank) {
        this(name);

        this.setAliases(aliases);
        this.rank = rank;
        this.playersOnly = true;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            if (this.playersOnly) {
                sender.sendMessage(CC.RED + "This command can only be performed by players.");
                return true;
            }
            this.execute(sender, args);
            return true;
        }
        Player player = (Player) sender;

        if (!UserUtil.checkRank(player, this.rank)) {
            player.sendMessage(CC.RED + "This command is limited to " + this.rank.getName() + " and above.");
            return true;
        }
        this.execute(player, args);
        return true;
    }

    public void execute(Player player, String[] args) {
    }

    public void execute(CommandSender sender, String[] args) {
    }
}
