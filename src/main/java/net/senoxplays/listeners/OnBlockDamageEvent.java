package net.senoxplays.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class OnBlockDamageEvent implements Listener {

    @EventHandler
    public void onBlockDamageEvent(EntityExplodeEvent event) {
        List<Block> blockList = new ArrayList<Block>(event.blockList());
        for (Block block : blockList) {
            if (block.getType() == Material.TRAPPED_CHEST) event.blockList().remove(block);
        }
    }
}
