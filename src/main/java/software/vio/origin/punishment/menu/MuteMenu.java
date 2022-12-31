package software.vio.origin.punishment.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import software.vio.origin.menu.Menu;
import software.vio.origin.menu.button.Button;
import software.vio.origin.menu.button.event.ButtonClickEvent;
import software.vio.origin.punishment.Punishment;
import software.vio.origin.punishment.PunishmentType;
import software.vio.origin.punishment.reason.MuteReason;
import software.vio.origin.util.CC;
import software.vio.origin.util.TimeUtil;
import software.vio.origin.util.item.ItemBuilder;

public class MuteMenu extends Menu {

    private final Player toPunish;

    public MuteMenu(Player viewer, Player target) {
        super(viewer, "Mute " + target.getName(), 3);

        this.toPunish = target;
    }

    @Override
    protected void setup() {
        Punishment punishment = new Punishment();

        punishment.setType(PunishmentType.MUTE);
        punishment.setTarget(this.toPunish.getUniqueId());
        punishment.setSender(this.getViewer().getUniqueId());

        this.surround(new ItemBuilder(Menu.FILL_ITEM).get());

        for (MuteReason reason : MuteReason.values()) {
            this.add(new Button(new ItemBuilder(Material.PAPER)
                    .setName(CC.GOLD + reason.getName())
                    .setLore("",
                            CC.GRAY + "Duration: " + CC.WHITE + TimeUtil.getPrettyTime(reason.getDuration()),
                            "",
                            CC.YELLOW + "Click to punish!")
                    .get()) {
                @Override
                public void onClick(ButtonClickEvent event) {
                    punishment.setReason(reason.getName());
                    punishment.setDuration(reason.getDuration());

                    punishment.issue();
                    event.getMenu().close();
                }
            });
        }
    }
}
