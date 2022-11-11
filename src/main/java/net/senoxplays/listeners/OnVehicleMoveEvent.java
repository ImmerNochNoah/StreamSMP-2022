package net.senoxplays.listeners;

import net.senoxplays.api.BlockRadius;
import net.senoxplays.chest_protection.ChestProtection;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;

public class OnVehicleMoveEvent implements Listener {

    @EventHandler
    public void onVehicleMoveEvent(VehicleMoveEvent event) {
        if (event.getVehicle().getName().equalsIgnoreCase("Minecart with Hopper")) {
            if (BlockRadius.getBlocksFromVehicle(event.getVehicle(), 1).contains(Material.TRAPPED_CHEST)) {
                ItemStack drop = new ItemStack(Material.HOPPER_MINECART, 1);
                World world = event.getVehicle().getWorld();
                world.dropItem(event.getVehicle().getLocation(), drop);
                event.getVehicle().remove();
            }
        }
    }
}
