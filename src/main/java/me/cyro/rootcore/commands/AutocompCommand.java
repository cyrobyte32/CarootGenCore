package me.cyro.rootcore.commands;

import me.cyro.rootcore.Rootcore;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AutocompCommand implements CommandExecutor {

    /*
    Auto Compressing is a feature of the GENS gamemode.
    Auto Compress Recipes allow to automatically turn a set amount of an Item into its 'Compressed' Variant
    This command allows admins to manage said recipes
     */



    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Rootcore plugin = Rootcore.getPlugin();

        if (!(sender instanceof Player p)) return true;

        if(!p.getWorld().getName().equals("void")) { // World void = Gens Game mode
            p.sendMessage("§eThis command can not be used from your current context. Go to the gens world to modify auto comps.");
        }

        // /autocomp (No args) -> Toggle logic would go here
        if (args.length == 0) {
            p.sendMessage("§eAuto-compress toggle coming soon!");
            return true;
        }

        if (args[0].equalsIgnoreCase("admin")) {
            if (!p.hasPermission("carootgencore.admin")) {
                p.sendMessage("§cNo permission.");
                return true;
            }

            // --- LIST FEATURE ---
            if (args.length == 2 && args[1].equalsIgnoreCase("list")) {
                ConfigurationSection section = plugin.getConfig().getConfigurationSection("autocomps");

                if (section == null || section.getKeys(false).isEmpty()) {
                    p.sendMessage("§cNo auto-compressions configured.");
                    return true;
                }

                p.sendMessage("§6§l--- Auto-Compress Recipes ---");
                for (String key : section.getKeys(false)) {
                    ItemStack src = section.getItemStack(key + ".source");
                    ItemStack res = section.getItemStack(key + ".result");

                    if (src != null && res != null) {
                        p.sendMessage("§e" + src.getType() + " §7(x" + src.getAmount() + ") §6➔ §a" + res.getType() + " §7(x" + res.getAmount() + ")");
                    }
                }
                p.sendMessage("§6§l-----------------------------");
                return true;
            }

            // --- ADD / REMOVE FEATURES ---
            if (args.length != 2) {
                p.sendMessage("§cUsage: /autocomp admin <add|remove|list>");
                return true;
            }

            if (args[1].equalsIgnoreCase("add")) {
                ItemStack source = p.getInventory().getItemInMainHand();
                ItemStack result = p.getInventory().getItemInOffHand();

                if (source.getType() == Material.AIR || result.getType() == Material.AIR) {
                    p.sendMessage("§cHold source in Main Hand and result in Off-Hand!");
                    return true;
                }

                plugin.getConfig().set("autocomps." + source.getType() + ".source", source);
                plugin.getConfig().set("autocomps." + source.getType() + ".result", result);
                plugin.saveConfig();
                plugin.reloadConfig(); // Immediate update!

                p.sendMessage("§aAdded: §f" + source.getAmount() + " " + source.getType() + " §7-> §f" + result.getAmount() + " " + result.getType());

            } else if (args[1].equalsIgnoreCase("remove")) {
                Material mat = p.getInventory().getItemInMainHand().getType();
                plugin.getConfig().set("autocomps." + mat, null);
                plugin.saveConfig();
                plugin.reloadConfig();

                p.sendMessage("§aRemoved auto-comp for " + mat);
            }
        }
        return true;
    }
}