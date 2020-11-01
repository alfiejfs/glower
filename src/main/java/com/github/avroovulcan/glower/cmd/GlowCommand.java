package com.github.avroovulcan.glower.cmd;

import com.github.avroovulcan.glower.Glower;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlowCommand implements CommandExecutor
{

    private final Glower plugin;

    public GlowCommand(Glower plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only a player can open the glowing menu...");
            return true;
        }

        Player player = (Player) sender;
        plugin.openGlowInventory(player);
        return true;
    }
}
