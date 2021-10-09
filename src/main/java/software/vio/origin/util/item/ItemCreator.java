package software.vio.origin.util.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import software.vio.origin.util.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ItemCreator {

    private final ItemStack item;

    /**
     * Create a new item creator from scratch
     *
     * @param material Material of the base item
     */

    public ItemCreator(Material material) {
        this.item = new ItemStack(material);
    }

    /**
     * Create a new item creator for an existing item stack
     *
     * @param item Base item
     */

    public ItemCreator(ItemStack item) {
        this.item = item;
    }

    /**
     * Set the name of the item
     *
     * @param name Name of the item
     * @return Item creator object
     */

    public ItemCreator setName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(CC.translate(name));
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the lore of the item using multiple strings
     *
     * @param lore Lore of the item
     * @return Item creator object
     */

    public ItemCreator setLore(String... lore) {
        ItemMeta meta = this.item.getItemMeta();
        List<String> finalLore = new ArrayList<>();
        Stream.of(lore).forEach(s -> finalLore.add(CC.translate(s)));
        meta.setLore(finalLore);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the lore of the item using a list
     *
     * @param lore List containing the lore of the item
     * @return Item creator object
     */

    public ItemCreator setLore(List<String> lore) {
        ItemMeta meta = this.item.getItemMeta();
        List<String> finalLore = new ArrayList<>();
        lore.forEach(s -> finalLore.add(CC.translate(s)));
        meta.setLore(finalLore);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the amount of the item
     *
     * @param amount Amount of the item
     * @return Item creator object
     */

    public ItemCreator setAmount(int amount) {
        // In case the amount is above 64
        this.item.setAmount(Math.min(amount, 64));
        return this;
    }

    /**
     * Set the durability of the item
     *
     * @param durability Durability of the item
     * @return Item creator object
     */

    public ItemCreator setDurability(int durability) {
        this.item.setDurability((short) durability);
        return this;
    }

    /**
     * Convert the item creator and build the item stack
     *
     * @return Item stack
     */

    public ItemStack get() {
        return this.item;
    }
}
