package net.senoxplays.listeners;

import net.senoxplays.beacon_chunkloader.BeaconChunkloader;
import net.senoxplays.chest_protection.ChestProtection;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnPlayerBreakBlockEvent implements Listener {

    @EventHandler
    public void onPlayerBreakBlockEvent(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Block b = event.getBlock();

        if (b.getType().equals(Material.BEACON)) {
            BeaconChunkloader.removeBeacon(p, b);
        }

        if (b.getType().equals(Material.TRAPPED_CHEST)) {
            event.setCancelled(ChestProtection.breakChest(p, b));
        }
    }
}
