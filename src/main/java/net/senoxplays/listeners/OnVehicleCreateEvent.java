package net.senoxplays.listeners;

import net.senoxplays.api.BlockRadius;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleEvent;

public class OnVehicleCreateEvent implements Listener {

    @EventHandler
    public void onVehicleEvent(VehicleCreateEvent event) {
        if (event.getVehicle().getName().equalsIgnoreCase("Minecart with Hopper")) {
            if (BlockRadius.getBlocksFromVehicle(event.getVehicle(), 1).contains(Material.TRAPPED_CHEST)) {
                event.setCancelled(true);
            }
        }
    }
}
