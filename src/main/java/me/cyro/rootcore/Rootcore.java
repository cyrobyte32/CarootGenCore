package me.cyro.rootcore;

import me.cyro.rootcore.commands.AutocompCommand;
import me.cyro.rootcore.commands.LobbyCommand;
import me.cyro.rootcore.commands.ReloadCommand;
import me.cyro.rootcore.commands.TutorialCommand;
import me.cyro.rootcore.listeners.AutoPickupListener;
import me.cyro.rootcore.listeners.ChatFilterListener;
import me.cyro.rootcore.listeners.JoinListener;
import me.cyro.rootcore.listeners.LeaveListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rootcore extends JavaPlugin implements CommandExecutor {

    private LeaderboardManager lbManager;
    private static Rootcore plugin;

    public static Rootcore getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // initialize Config
        saveDefaultConfig();

        plugin = this;

        // Initialize Manager
        this.lbManager = new LeaderboardManager();

        // Register Listeners & Commands
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new AutoPickupListener(), this);
        getCommand("leaderboard").setExecutor(this);
        getCommand("autocomp").setExecutor(new AutocompCommand());
        getCommand("lobby").setExecutor(new LobbyCommand());
        getCommand("tutorial").setExecutor(new TutorialCommand());
        getCommand("rootcorereloadconfig").setExecutor(new ReloadCommand());
        getServer().getPluginManager().registerEvents(new ChatFilterListener(), this);

        // Load holograms from config (Delay by 1 tick to ensure DH is ready)
        Bukkit.getScheduler().runTaskLater(this, this::loadAndStartLeaderboards, 1L);
    }

    public void loadAndStartLeaderboards() {
        // Helper to load specific boards if they exist in config
        loadBoard("kills", "kills-lb", "statistic_player_kills", "§6§lTOP KILLERS");
        loadBoard("blocks", "blocks-lb", "statistic_mine_block", "§b§lTOP MINERS");
        loadBoard("playtime", "playtime-lb", "statistic_time_played", "§a§lTOP PLAYTIME");
    }

    private void loadBoard(String configKey, String holoName, String ajlbName, String title) {
        Location loc = getConfig().getLocation("locations." + configKey);
        if (loc != null) {
            lbManager.createLeaderboard(holoName, loc, ajlbName, title);
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true; // only useable by players

        if (args.length < 2 || !args[0].equalsIgnoreCase("set")) {
            player.sendMessage("§cUsage: /leaderboard set <kills|blocks|playtime>");
            return true;
        }

        String type = args[1].toLowerCase();

        // 1. Save to Config
        getConfig().set("locations." + type, player.getLocation());
        saveConfig();

        // 2. Call the Manager
        String holoName = type + "-lb";
        String boardName = type.equals("playtime") ? "statistic_time_played" : "statistic_player_" + type;
        lbManager.createLeaderboard(holoName, player.getLocation(), boardName, "§6§lTOP " + type.toUpperCase());

        player.sendMessage("§aLeaderboard location for " + type + " has been updated!");
        return true;
    }
}