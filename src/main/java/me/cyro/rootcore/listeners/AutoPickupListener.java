package me.cyro.rootcore.listeners;

import me.cyro.rootcore.Rootcore;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;

public class AutoPickupListener implements Listener {

    /*
    This Listener is responsible for the Auto-Pickup feature and implementing the Auto compress recipes.
    The Auto Pickup Feature adds drops from broken blocks directly to a players inventory and prevents the drops.
    The Auto Compress Feature automatically uses set recipes to automatically convert a set amount of an item into a new item.
     */


    // Set priority to HIGHEST to ensure other plugins (like world guard) don't conflict
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        Rootcore plugin = Rootcore.getPlugin();

        if(!p.getWorld().getName().equals("void")) return; // check correct context

        // 1. Get drops based on the player's current tool (Fixes Fortune/Silk Touch)
        Collection<ItemStack> drops = block.getDrops(p.getInventory().getItemInMainHand());

        // 2. Add container contents if it's a chest/furnace/etc.
        // 2. Add container contents ONLY if it's NOT a Shulker Box
        if (block.getState() instanceof Container) {
            return;
        }


        // 3. Process the drops
        for (ItemStack item : drops) {
            if (item == null || item.getType() == Material.AIR) continue;

            // addItem returns items that DID NOT fit
            Map<Integer, ItemStack> leftovers = p.getInventory().addItem(item);

            // Drop leftovers at the block location if inventory is full
            if (!leftovers.isEmpty()) {
                for (ItemStack remaining : leftovers.values()) {
                    block.getWorld().dropItemNaturally(block.getLocation(), remaining);
                }
            }
        }

        // --- Fixed Auto-Compress Logic ---
// 1. Get ONLY the 36 main storage slots
        ItemStack[] storage = p.getInventory().getStorageContents();

        for (int i = 0; i < storage.length; i++) {
            ItemStack item = storage[i];
            if (item == null || item.getType() == Material.AIR) continue;

            String path = "autocomps." + item.getType();
            ItemStack source = plugin.getConfig().getItemStack(path + ".source");
            ItemStack result = plugin.getConfig().getItemStack(path + ".result");

            if (source != null && result != null && item.isSimilar(source)) {

                // Calculate how many results we SHOULD get
                int compressions = item.getAmount() / source.getAmount();

                if (compressions > 0) {
                    ItemStack resultToGive = result.clone();
                    resultToGive.setAmount(result.getAmount() * compressions);

                    // 2. Subtract source items FIRST
                    int remainder = item.getAmount() % source.getAmount();
                    item.setAmount(remainder);
                    p.getInventory().setItem(i, remainder <= 0 ? null : item);

                    // 3. Manually add to storage (STRICTLY ignoring off-hand)
                    addToStrictStorage(p, resultToGive, block.getLocation());
                }
            }
        }
        p.updateInventory();

        // Cancel the actual block drops since we handled it manually
        e.setDropItems(false);
    }



    private void addToStrictStorage(Player player, ItemStack itemToAdd, org.bukkit.Location dropLoc) {
        ItemStack[] contents = player.getInventory().getStorageContents();

        // First Pass: Try to stack with existing items
        for (int i = 0; i < 36; i++) {
            ItemStack slot = contents[i];
            if (slot != null && slot.isSimilar(itemToAdd)) {
                int canAdd = slot.getMaxStackSize() - slot.getAmount();
                int adding = Math.min(canAdd, itemToAdd.getAmount());

                if (adding > 0) {
                    slot.setAmount(slot.getAmount() + adding);
                    itemToAdd.setAmount(itemToAdd.getAmount() - adding);
                }
            }
            if (itemToAdd.getAmount() <= 0) break;
        }

        // Second Pass: Try to find empty slots
        if (itemToAdd.getAmount() > 0) {
            for (int i = 0; i < 36; i++) {
                if (contents[i] == null || contents[i].getType() == Material.AIR) {
                    int adding = Math.min(itemToAdd.getMaxStackSize(), itemToAdd.getAmount());
                    ItemStack newStack = itemToAdd.clone();
                    newStack.setAmount(adding);
                    contents[i] = newStack;
                    itemToAdd.setAmount(itemToAdd.getAmount() - adding);
                }
                if (itemToAdd.getAmount() <= 0) break;
            }
        }

        // Apply the changes to the 36 main slots ONLY
        player.getInventory().setStorageContents(contents);

        // Third Pass: Drop leftovers on the ground
        if (itemToAdd.getAmount() > 0) {
            dropLoc.getWorld().dropItemNaturally(dropLoc, itemToAdd);
        }
    }

}