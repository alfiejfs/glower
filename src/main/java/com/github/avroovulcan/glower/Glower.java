package com.github.avroovulcan.glower;

import com.github.avroovulcan.glower.cmd.GlowCommand;
import com.github.avroovulcan.glower.event.InventoryClickListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Glower extends JavaPlugin
{

    private List<UUID> colouring;
    private List<String> possible;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        colouring = new ArrayList<>();
        setupPossible();
        getCommand("glower").setExecutor(new GlowCommand(this));
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
    }

    public void openGlowInventory(Player p)
    {
        Inventory i = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&',
            getConfig().getString("inventory-name")));
        for(String s : possible)
        {
            if(p.hasPermission("glower." + ChatColor.stripColor(s)))
            {
                Wool wool = new Wool(DyeColor.valueOf(ChatColor.stripColor(s).toUpperCase()));
                ItemStack is = wool.toItemStack(1);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(s);
                is.setItemMeta(im);
                i.addItem(is);
            }
        }

        ItemStack is = new ItemStack(Material.BARRIER);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.WHITE + "Remove Outline");
        is.setItemMeta(im);
        i.addItem(is);

        p.openInventory(i);
        colouring.add(p.getUniqueId());
    }

    private void setupPossible()
    {
        possible = new ArrayList<>();
        possible.add(ChatColor.RED + "Red");
        possible.add(ChatColor.BLUE + "Blue");
        possible.add(ChatColor.BLACK + "Black");
        possible.add(ChatColor.WHITE + "White");
        possible.add(ChatColor.YELLOW + "Yellow");
        possible.add(ChatColor.GREEN + "Green");
        possible.add(ChatColor.LIGHT_PURPLE + "Pink");
        possible.add(ChatColor.DARK_PURPLE + "Purple");
    }

    public List<UUID> getColouring()
    {
        return this.colouring;
    }
}
