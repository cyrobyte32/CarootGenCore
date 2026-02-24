package me.cyro.rootcore.listeners;

import me.cyro.rootcore.Rootcore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatFilterListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        String prefix = "§x§F§C§A§A§5§C§lʀ§x§F§C§A§F§5§C§lᴏ§x§F§C§B§5§5§C§lᴏ§x§F§C§B§A§5§C§lᴛ§x§F§C§C§0§5§C§lᴄ§x§F§C§C§5§5§C§lᴏ§x§F§C§C§B§5§C§lʀ§x§F§C§D§0§5§C§lᴇ §r§6▶ §r";
        String msg = e.getMessage();
        Player p = e.getPlayer();
        List<String> filter = Rootcore.getPlugin().getConfig().getStringList("filteredWords");

        if(p.hasPermission("rootcore.bypassfilter")) return;

        for (String word : filter) {
            if (msg.contains(word)) {
                e.setCancelled(true);
                p.sendMessage(prefix + "§cYour message contained a blocked word and could not be delivered.");
                Rootcore.getPlugin().getLogger().info("[RootcoreFilter] Message '" + msg + "' by Player " + p.getDisplayName() + " was blocked for containing: " + word);
            }

        }

    }
}
