package me.cyro.rootcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        p.teleport(Bukkit.getWorld("lobby").getSpawnLocation());
        p.removePotionEffect(PotionEffectType.JUMP_BOOST);

        if(!p.hasPlayedBefore()) {
           e.setJoinMessage("§b§l"+p.getDisplayName()+" §x§6§3§D§8§C§8§lj§x§6§8§C§E§C§A§lo§x§6§C§C§5§C§D§li§x§7§1§B§B§C§F§ln§x§7§5§B§1§D§2§le§x§7§9§A§7§D§5§ld §x§8§2§9§4§D§A§lf§x§8§6§8§A§D§D§lo§x§8§B§8§0§D§F§lr §x§9§4§6§D§E§4§lt§x§9§8§6§3§E§7§lh§x§9§E§6§0§E§1§le §x§A§A§5§A§D§4§lf§x§A§F§5§6§C§E§li§x§B§5§5§3§C§8§lr§x§B§B§5§0§C§2§ls§x§C§1§4§D§B§B§lt §x§C§D§4§7§A§F§lt§x§D§2§4§3§A§9§li§x§D§8§4§0§A§2§lm§x§D§E§3§D§9§C§le§x§E§4§3§A§9§6§l!");
            p.getInventory().addItem(new ItemStack(Material.COOKED_PORKCHOP, 64));
        } else {
            e.setJoinMessage("§a▶ §7"+p.getDisplayName());
        }

    }
}
