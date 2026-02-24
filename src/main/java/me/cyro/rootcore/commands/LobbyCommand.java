package me.cyro.rootcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player p) {

            if(strings.length > 0) {
                p.sendMessage("§cToo many arguments. Use /lobby");
                return true;
            }

            if(p.getWorld().getName().equals("lobby")) {
                p.sendMessage("§cYou are already in the Lobby.");
                return true;
            }

            p.teleport(Bukkit.getWorld("lobby").getSpawnLocation());
            p.sendMessage("§x§F§C§A§A§5§C§lʀ§x§F§C§A§F§5§C§lᴏ§x§F§C§B§5§5§C§lᴏ§x§F§C§B§A§5§C§lᴛ§x§F§C§C§0§5§C§lᴄ§x§F§C§C§5§5§C§lᴏ§x§F§C§C§B§5§C§lʀ§x§F§C§D§0§5§C§lᴇ §r§6▶ §r"
            +"§aSent you to the Lobby.");

        }

        return true;
    }
}
