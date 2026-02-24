package me.cyro.rootcore.commands;

import me.cyro.rootcore.Rootcore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player p) {
            if(p.hasPermission("rootcore.admin")) {
                Rootcore.getPlugin().reloadConfig();
                p.sendMessage("§aConfig Reloaded.");
            }
        }


        return true;
    }
}
