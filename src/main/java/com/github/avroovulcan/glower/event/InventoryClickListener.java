package com.github.avroovulcan.glower.event;

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

  private Glower instance;

  public InventoryClickListener(Glower instance) {
    this.instance = instance;
  }


  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    if (!instance.getColouring().contains(e.getWhoClicked().getUniqueId())) {
      return;
    }
    if (e.getCurrentItem() == null) {
      return;
    }
    if (e.getCurrentItem().getType() == Material.AIR) {
      return;
    }
    e.setCancelled(true);

    if (e.getCurrentItem().getType() == Material.BARRIER) {
      e.getWhoClicked().removePotionEffect(PotionEffectType.GLOWING);
    } else {
      switch (GlowColor.valueOf(
          ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase())) {
        case RED:
          glow(e.getWhoClicked(), Color.RED, ChatColor.RED);
          break;
        case BLUE:
          glow(e.getWhoClicked(), Color.AQUA, ChatColor.AQUA);
          break;
        case BLACK:
          glow(e.getWhoClicked(), Color.BLACK, ChatColor.BLACK);
          break;
        case WHITE:
          glow(e.getWhoClicked(), Color.WHITE, ChatColor.WHITE);
          break;
        case YELLOW:
          glow(e.getWhoClicked(), Color.YELLOW, ChatColor.YELLOW);
          break;
        case GREEN:
          glow(e.getWhoClicked(), Color.LIME, ChatColor.GREEN);
          break;
        case PINK:
          glow(e.getWhoClicked(), Color.FUCHSIA, ChatColor.LIGHT_PURPLE);
          break;
        case PURPLE:
          glow(e.getWhoClicked(), Color.PURPLE, ChatColor.DARK_PURPLE);
          break;
      }
    }

    e.getWhoClicked().closeInventory();
    instance.getColouring().remove(e.getWhoClicked().getUniqueId());
  }

  /**
   * @param entity - human entity to add glow effect
   * @param color - colour player is going to glow
   */
  private void glow(HumanEntity entity, Color color, ChatColor teamCol) {
    Player p = (Player) entity;
    PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, 9999999, 10, false, false,
        color);

    Scoreboard board = p.getScoreboard();
    if (board == null) {
      board = Bukkit.getScoreboardManager().getNewScoreboard();
    }
    if (board.getTeam(p.getName() + "colour") != null) {
      board.getTeam(p.getName() + "colour").unregister();
    }

    Team team = board.registerNewTeam(p.getName() + "colour");
    team.setColor(teamCol);
    team.setPrefix(teamCol + "");
    team.addEntry(p.getName());

    p.setScoreboard(board);
    p.addPotionEffect(glow);
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent e) {
    instance.getColouring().remove(e.getPlayer().getUniqueId());
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    instance.getColouring().remove(e.getPlayer().getUniqueId());
  }
}
