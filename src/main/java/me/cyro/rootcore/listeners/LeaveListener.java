package me.cyro.rootcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    /*
    Simple Custom Leave Message
     */

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        e.setQuitMessage("§c◀ §7"+p.getDisplayName());

    }
}
