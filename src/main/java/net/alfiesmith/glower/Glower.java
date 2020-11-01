package net.alfiesmith.glower;

import net.alfiesmith.glower.cmd.GlowCommand;
import net.alfiesmith.glower.listener.InventoryClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;

public class Glower extends JavaPlugin {

  private final static List<String> POSSIBLE_COLORS = Arrays.asList(
      ChatColor.RED + "Red",
      ChatColor.BLUE + "Blue",
      ChatColor.BLACK + "Black",
      ChatColor.WHITE + "White",
      ChatColor.YELLOW + "Yellow",
      ChatColor.GREEN + "Green",
      ChatColor.LIGHT_PURPLE + "Pink",
      ChatColor.DARK_PURPLE + "Purple"
  );

  private final static ItemStack CLEAR_ITEM = new ItemStack(Material.BARRIER);

  static {
    ItemMeta clearMeta = CLEAR_ITEM.getItemMeta();
    clearMeta.setDisplayName(ChatColor.WHITE + "Remove Outline");
    CLEAR_ITEM.setItemMeta(clearMeta);
  }

  private List<UUID> pickingColour;


  @Override
  public void onEnable() {
    saveDefaultConfig();

    this.pickingColour = new ArrayList<>();
    getCommand("glower").setExecutor(new GlowCommand(this));

    Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
  }

  public void openGlowInventory(Player player) {
    Inventory inventory = Bukkit.createInventory(null, 9,
        ChatColor.translateAlternateColorCodes('&',
            getConfig().getString("inventory-name")));

    for (String color : POSSIBLE_COLORS) {
      if (player.hasPermission("glower." + ChatColor.stripColor(color))) {
        Wool wool = new Wool(DyeColor.valueOf(ChatColor.stripColor(color).toUpperCase()));

        ItemStack item = wool.toItemStack(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color);
        item.setItemMeta(meta);

        inventory.addItem(item);
      }
    }

    inventory.addItem(CLEAR_ITEM);
    player.openInventory(inventory);
    pickingColour.add(player.getUniqueId());
  }

  public void removePickingColor(UUID uuid) {
    this.pickingColour.remove(uuid);
  }

  public boolean isPickingColor(UUID uuid) {
    return this.pickingColour.contains(uuid);
  }
}
