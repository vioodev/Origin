package software.vio.origin.punishment.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import software.vio.origin.menu.Menu;
import software.vio.origin.menu.button.Button;
import software.vio.origin.menu.button.event.ButtonClickEvent;
import software.vio.origin.util.CC;
import software.vio.origin.util.item.ItemCreator;

public class PunishmentMenu extends Menu {

    private final Player target;

    public PunishmentMenu(Player viewer, Player target) {
        super(viewer, "Punish " + target.getName(), 3);

        this.target = target;
    }

    @Override
    protected void setup() {
        this.set(10, new Button(new ItemCreator(Material.BOOK)
                .setName(CC.GOLD + "Mute")
                .setLore("",
                        CC.GRAY + "Limited chat access.",
                        "",
                        CC.YELLOW + "Click to select!")
                .get()) {
            @Override
            public void onClick(ButtonClickEvent event) {
                new MuteMenu(event.getPlayer(), target).open();
            }
        });
        this.set(12, new Button(new ItemCreator(Material.BOOK_AND_QUILL)
                .setName(CC.GOLD + "Kick")
                .setLore("",
                        CC.GRAY + "One-time disconnect.",
                        "",
                        CC.YELLOW + "Click to select!")
                .get()) {
            @Override
            public void onClick(ButtonClickEvent event) {
                new KickMenu(event.getPlayer(), target).open();
            }
        });
        this.set(14, new Button(new ItemCreator(Material.ENCHANTED_BOOK)
                .setName(CC.GOLD + "Ban")
                .setLore("",
                        CC.GRAY + "Temporary or permanent suspension.",
                        "",
                        CC.YELLOW + "Click to select!")
                .get()) {
            @Override
            public void onClick(ButtonClickEvent event) {
                new BanMenu(event.getPlayer(), target).open();
            }
        });
        this.set(16, new Button(new ItemCreator(Material.BOOKSHELF)
                .setName(CC.GOLD + "Blacklist")
                .setLore("",
                        CC.GRAY + "Permanent and un-appealable suspension.",
                        "",
                        CC.YELLOW + "Click to select!")
                .get()) {
            @Override
            public void onClick(ButtonClickEvent event) {
                new BlacklistMenu(event.getPlayer(), target).open();
            }
        });
        this.fill(Menu.FILL_ITEM);
    }
}
