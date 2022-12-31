package software.vio.origin.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import software.vio.origin.util.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;


    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
        meta = this.item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        meta.setDisplayName(CC.translate(name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        List<String> finalLore = new ArrayList<>();
        Stream.of(lore).forEach(s -> finalLore.add(CC.translate(s)));
        meta.setLore(finalLore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<String> finalLore = new ArrayList<>();
        lore.forEach(s -> finalLore.add(CC.translate(s)));
        meta.setLore(finalLore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(Math.min(amount, 64));
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        item.setDurability((short) durability);
        return this;
    }

    public ItemStack build() {
        return item;
    }
}
