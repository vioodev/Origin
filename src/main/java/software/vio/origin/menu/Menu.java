package software.vio.origin.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import software.vio.origin.menu.button.Button;
import software.vio.origin.util.MathUtil;
import software.vio.origin.util.item.ItemCreator;
import software.vio.origin.util.task.TaskUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

// TODO: Add menu sounds and look into removing menus from the map when a menu is closed
// TODO: Add pages (perhaps auto incrementing pages with Menu#add?)

@Getter
public abstract class Menu {

    public static final ItemStack FILL_ITEM = new ItemCreator(Material.STAINED_GLASS_PANE).setName(" ").setDurability(7).get();
    // Menus are per-player inventories - storing the UUID of the player alongside the menu
    private static final Map<UUID, Menu> menus = new HashMap<>();
    private final Player viewer;
    private final Inventory inventory;
    private final Map<Integer, Button> buttons;
    /**
     * Create a new menu without automatic updating
     *
     * @param viewer Player the menu is displayed to
     * @param title  Title of the menu
     * @param rows   Amount of rows (size / 9)
     */

    public Menu(Player viewer, String title, int rows) {
        this.viewer = viewer;
        this.inventory = Bukkit.createInventory(null, rows * 9, title);
        this.buttons = new HashMap<>();

        menus.put(viewer.getUniqueId(), this);
    }

    /**
     * Create a new menu with automatic updating
     *
     * @param updateInterval Interval in which the menu is updated in ticks
     */

    public Menu(Player viewer, String title, int rows, long updateInterval) {
        this.viewer = viewer;
        this.inventory = Bukkit.createInventory(null, rows * 9, title);
        this.buttons = new HashMap<>();

        menus.put(viewer.getUniqueId(), this);

        TaskUtil.runTimer(this::update, 0L, updateInterval);
    }

    /**
     * Get an existing menu by the viewer's UUID
     *
     * @param uuid UUID of the inventory viewer
     * @return Menu of the viewer, null if no menu is stored
     */

    public static Menu getByUuid(UUID uuid) {
        return menus.get(uuid);
    }

    /**
     * Get a certain button in the menu
     *
     * @param slot Inventory slot of the button
     * @return Button placed on the slot, null if the slot doesn't contain a button
     */

    public Button getButtonBySlot(int slot) {
        return this.buttons.get(slot);
    }

    /**
     * Add an item stack to the menu
     *
     * @param item Item stack that is added
     */

    protected void add(ItemStack item) {
        this.inventory.addItem(item);
    }

    /**
     * Add an item stack to a specific slot in the menu
     *
     * @param slot Where to add the item stack
     * @param item Item stack that is added
     */

    protected void set(int slot, ItemStack item) {
        this.inventory.setItem(slot, item);
    }

    /**
     * Add a clickable button to the menu
     *
     * @param button Button that is added
     */

    protected void add(Button button) {
        // Does the same as Inventory#addItem - information about the slot is needed for the button map
        int slot = this.inventory.firstEmpty();
        this.inventory.setItem(slot, button.getItem());
        this.buttons.put(slot, button);
    }

    /**
     * Add a clickable button a specific slot in the menu
     *
     * @param slot   Where to add the button
     * @param button Button that is added
     */

    protected void set(int slot, Button button) {
        this.inventory.setItem(slot, button.getItem());
        this.buttons.put(slot, button);
    }

    /**
     * Fill all empty slots in the menu with a specific item stack
     *
     * @param item Item stack filling out empty slots
     */

    protected void fill(ItemStack item) {
        IntStream.range(0, this.inventory.getSize())
                .filter(slot -> this.inventory.getItem(slot) == null)
                .forEach(slot -> this.inventory.setItem(slot, item));
    }

    /**
     * Fill all outer slots of the menu with a specific item stack
     *
     * @param item
     */

    protected void surround(ItemStack item) {
        IntStream.range(0, this.inventory.getSize())
                .filter(slot -> this.inventory.getItem(slot) == null)
                .filter(slot -> slot < 9 ||
                        slot > this.inventory.getSize() - 9 ||
                        MathUtil.isDivisible(slot, 9) ||
                        MathUtil.isDivisible(slot + 1, 9))
                .forEach(slot -> this.inventory.setItem(slot, item));
    }

    /**
     * Open the menu for the viewer specified in the constructor
     */

    public void open() {
        this.setup();
        this.viewer.openInventory(this.inventory);
    }

    /**
     * Close the menu for the viewer
     */

    public void close() {
        this.viewer.closeInventory();
        menus.remove(this.viewer.getUniqueId(), this);
    }

    /**
     * Reload everything in the menu
     */

    public void update() {
        this.inventory.clear();
        this.setup();
    }

    /**
     * On implementation, all contents should be added to the menu here
     */

    protected abstract void setup();
}
