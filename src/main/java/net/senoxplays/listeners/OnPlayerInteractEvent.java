package net.senoxplays.listeners;

import net.senoxplays.chest_protection.ChestProtection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;

public class OnPlayerInteractEvent implements Listener {

    private Arrow arrow;
    public static HashMap<Player, Arrow> arrowHashMap = new HashMap<>();

    @EventHandler
    public void onPlayerInteactEvent(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){

            if (event.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
                event.setCancelled(ChestProtection.openChest(p, event.getClickedBlock()));
            }

            if(p.getItemInHand().getType() == Material.AIR){
                switch (event.getClickedBlock().getType()) {
                    case SPRUCE_STAIRS, COBBLESTONE_STAIRS, SANDSTONE_STAIRS, BIRCH_STAIRS, JUNGLE_STAIRS, CRIMSON_STAIRS, NETHER_BRICK_STAIRS, WARPED_STAIRS, ACACIA_STAIRS, DARK_OAK_STAIRS, OAK_STAIRS:
                        Location l = event.getClickedBlock().getLocation();
                        World w = p.getWorld();
                        arrow = w.spawn(l.add(0.5D, 0.01D, 0.5D), Arrow.class);
                        arrow.setPassenger(p);
                        removeArrow(p);
                        arrowHashMap.put(p, arrow);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        removeArrow(p);
    }

    public static void removeArrow(Player p) {
        if (arrowHashMap.containsKey(p)) {
            Arrow arrow2 = arrowHashMap.get(p);
            arrow2.remove();
        }
    }
}
