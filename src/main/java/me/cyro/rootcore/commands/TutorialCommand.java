package me.cyro.rootcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TutorialCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // Simple Tutorial Command
        // Allows players to view tutorial for Rootcore's Game modes

        if(commandSender instanceof Player p) {

            String prefix = "§x§F§C§A§A§5§C§lʀ§x§F§C§A§F§5§C§lᴏ§x§F§C§B§5§5§C§lᴏ§x§F§C§B§A§5§C§lᴛ§x§F§C§C§0§5§C§lᴄ§x§F§C§C§5§5§C§lᴏ§x§F§C§C§B§5§C§lʀ§x§F§C§D§0§5§C§lᴇ §r§6▶ §r";

            // /tutorial <smp/gens>
            if(strings.length == 0 || ( !strings[0].equalsIgnoreCase("smp") && !strings[0].equalsIgnoreCase("gens"))) {
                p.sendMessage(prefix+"§cPlease Specify what tutorial you want to read. Options: /tutorial <smp/gens>");
                return true;
            }

            else if(strings[0].equalsIgnoreCase("gens")) {
                p.sendMessage("§8------------------");
                p.sendMessage("§6§lGens §e§lTutorial");
                p.sendMessage("§8------------------");
                p.sendMessage("§6§l- §eGet Started by going to your first Gen: The wood.");
                p.sendMessage("§6§l- §eUse The trader at the gens to upgrade your gear and climb the Leaderboards!");
                p.sendMessage("§6§l- §eCheck out the Mob Arena for some awesome gear if youre ready for a battle.");
                p.sendMessage("§8------------------");
            } else if(strings[0].equalsIgnoreCase("smp")) {
                p.sendMessage("§8------------------");
                p.sendMessage("§6§lSMP §e§lTutorial");
                p.sendMessage("§8------------------");
                p.sendMessage("§6§l- §eTo Start, you should /rtp somewhere to find a spot to settle down");
                p.sendMessage("§6§l- §eYou can claim chunks with §a/claim§e to prevent griefing.");
                p.sendMessage("§6§l- §eThe World Generation is completely overhauled, and so are structures, a great place for you to explore!");
                p.sendMessage("§8------------------");
            }

        }

        return true;
    }
}
