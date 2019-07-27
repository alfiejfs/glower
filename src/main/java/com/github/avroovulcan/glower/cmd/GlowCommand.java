package com.github.avroovulcan.glower.cmd;

import com.github.avroovulcan.glower.Glower;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlowCommand implements CommandExecutor
{

    private Glower instance;

    public GlowCommand(Glower instance)
    {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        instance.openGlowInventory(p);
        return true;
    }
}
