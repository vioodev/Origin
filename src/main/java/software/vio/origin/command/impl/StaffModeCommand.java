package software.vio.origin.command.impl;

import software.vio.origin.Origin;
import software.vio.origin.command.OriginCommand;
import software.vio.origin.rank.Rank;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class StaffModeCommand extends OriginCommand {

    public StaffModeCommand() {
        super("staffmode", Arrays.asList("modmode", "staff", "mod"), Rank.TRAINEE);
    }

    @Override
    public void execute(Player player, String[] args) {
        Origin.getInstance().getUserManager().toggleStaffMode(player);
    }
}
