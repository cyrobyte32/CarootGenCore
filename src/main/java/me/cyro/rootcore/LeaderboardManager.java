package me.cyro.rootcore;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardManager {

    public void createLeaderboard(String name, Location loc, String boardName, String title) {

        // Creates a Leaderboard and saves it to the DecentHolograms Config

        List<String> lines = new ArrayList<>();
        lines.add(title);
        lines.add("§7-----------------");
        for (int i = 1; i <= 10; i++) {
            lines.add("§e" + i + ". §f%ajlb_lb_" + boardName + "_" + i + "_alltime_name% §7- §a%ajlb_lb_" + boardName + "_" + i + "_alltime_value%");
        }

        Hologram holo = DHAPI.getHologram(name);
        if (holo == null) {
            // The 'true' here makes it PERSISTENT (saved to DecentHolograms/holograms folder)
            DHAPI.createHologram(name, loc, true, lines);
        } else {
            DHAPI.moveHologram(holo, loc); // Use the API to move it if it already exists
            DHAPI.setHologramLines(holo, lines);
        }
    }
}