package net.senoxplays.listeners;

import net.senoxplays.Main;
import net.senoxplays.chest_protection.ChestProtection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnPlayerBlockPlaceEvent implements Listener {

    @EventHandler
    public void onPlayerBlockPlayeEvent(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        switch (event.getBlock().getType()) {
            case TRAPPED_CHEST, HOPPER:
                if (ChestProtection.checkIfThereIsATrappedChestAround(event.getBlock())) {
                    p.sendMessage(Main.getPrefix() + " §cDu kannst §e" + event.getBlock().getType().name() + " §chier nicht platzieren.");
                    event.setCancelled(true);
                }
                break;
        }
    }
}
