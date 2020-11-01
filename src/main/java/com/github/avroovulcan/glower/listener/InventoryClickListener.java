package com.github.avroovulcan.glower.listener;

import com.github.avroovulcan.glower.Glower;
import com.github.avroovulcan.glower.entity.GlowColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class InventoryClickListener implements Listener {

  private final Glower plugin;

  public InventoryClickListener(Glower plugin) {
    this.plugin = plugin;
  }


  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (!plugin.isPickingColor(event.getWhoClicked().getUniqueId())) {
      return;
    }

    if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
      return;
    }

    event.setCancelled(true);

    if (event.getCurrentItem().getType() == Material.BARRIER) {
      event.getWhoClicked().removePotionEffect(PotionEffectType.GLOWING);
    } else {
      switch (GlowColor.valueOf(
          ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())
              .toUpperCase())) {
        case RED:
          glow(event.getWhoClicked(), Color.RED, ChatColor.RED);
          break;
        case BLUE:
          glow(event.getWhoClicked(), Color.AQUA, ChatColor.AQUA);
          break;
        case BLACK:
          glow(event.getWhoClicked(), Color.BLACK, ChatColor.BLACK);
          break;
        case WHITE:
          glow(event.getWhoClicked(), Color.WHITE, ChatColor.WHITE);
          break;
        case YELLOW:
          glow(event.getWhoClicked(), Color.YELLOW, ChatColor.YELLOW);
          break;
        case GREEN:
          glow(event.getWhoClicked(), Color.LIME, ChatColor.GREEN);
          break;
        case PINK:
          glow(event.getWhoClicked(), Color.FUCHSIA, ChatColor.LIGHT_PURPLE);
          break;
        case PURPLE:
          glow(event.getWhoClicked(), Color.PURPLE, ChatColor.DARK_PURPLE);
          break;
      }
    }

    event.getWhoClicked().closeInventory();
    plugin.removePickingColor(event.getWhoClicked().getUniqueId());
  }

  /**
   * @param entity - human entity to add glow effect
   * @param color  - color player is going to glow
   */
  private void glow(HumanEntity entity, Color color, ChatColor teamColor) {
    Player player = (Player) entity;
    PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 10, false, false,
        color);

    Scoreboard scoreboard = player.getScoreboard();
    if (scoreboard == null) {
      scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }
    if (scoreboard.getTeam(player.getName() + "color") != null) {
      scoreboard.getTeam(player.getName() + "color").unregister();
    }

    Team team = scoreboard.registerNewTeam(player.getName() + "color");
    team.setColor(teamColor);
    team.setPrefix(teamColor + "");
    team.addEntry(player.getName());

    player.setScoreboard(scoreboard);
    player.addPotionEffect(glow);
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    this.plugin.removePickingColor(event.getPlayer().getUniqueId());
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    this.plugin.removePickingColor(event.getPlayer().getUniqueId());
  }
}
