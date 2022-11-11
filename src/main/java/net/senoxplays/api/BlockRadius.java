package net.senoxplays.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Vehicle;

import java.util.ArrayList;

public class BlockRadius {

    //https://www.spigotmc.org/threads/tutorial-getting-blocks-in-a-cube-radius.64981/

    public ArrayList<Material> getBlocks(Block start, int radius){
        ArrayList<Material> blocks = new ArrayList<Material>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock().getType());
                }
            }
        }
        blocks.remove(start.getType());
        return blocks;
    }

    public static ArrayList<Material> getBlocksFromVehicle(Vehicle start, int radius){
        ArrayList<Material> blocks = new ArrayList<Material>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock().getType());
                }
            }
        }
        return blocks;
    }
}
