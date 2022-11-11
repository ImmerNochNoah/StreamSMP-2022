package net.senoxplays.chest_protection;

import com.google.gson.Gson;
import net.senoxplays.Main;
import net.senoxplays.api.BlockRadius;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ChestProtection {

    static HashMap<String, String> protectedChests = new HashMap<>();

    public static int protectedChestsSize() {
        return protectedChests.size();
    }
    public static void protectChest(Player p, Block b) {
        if (b.getType().equals(Material.TRAPPED_CHEST)) {
            if (!ifChestProtected(b)) {
                BlockState chestState = b.getState();
                Chest chest = (Chest) chestState;
                if (chest.getInventory().getSize() != 54) {
                    protectedChests.put(getChestLocation(b), p.getUniqueId().toString());
                    p.sendMessage(Main.getPrefix() + " §aDu hast die Kiste gesichert.");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                } else {
                    p.sendMessage(" §4Du kannst keine Doppelkisten sichern!");
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                }
            } else {
                p.sendMessage(Main.getPrefix() + " §7Die Kiste ist bereits gesichert.");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            }
        }
    }

    public static void removeProtectedChest(Player p, Block b) {
        if (!ifChestProtected(b)) {
            p.sendMessage(Main.getPrefix() + " §7Die Kiste ist nicht gesichert.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
        } else {
            if (!protectedChests.get(getChestLocation(b)).equalsIgnoreCase(p.getUniqueId().toString())) {
                if (!p.isOp()) {
                    p.sendMessage(Main.getPrefix() + " §7Du bist nicht der Inhaber dieser Kiste.");
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    return;
                }
                p.sendMessage(Main.getPrefix() + " §eDu bist nicht der Inhaber dieser Kiste, konntest sie aber trotzdem entsichern, da du Server rechte hast!");
            }
            protectedChests.remove(getChestLocation(b));
            p.sendMessage(Main.getPrefix() + " §7Die Kiste ist nun §cnicht §7mehr gesichert.");
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }
    }

    public static boolean checkIfThereIsATrappedChestAround(Block b) {
        BlockRadius br = new BlockRadius();
        if (!br.getBlocks(b, 1).contains(Material.TRAPPED_CHEST)) {
            System.out.println(false);
            return false;
        } else {
            System.out.println(true);
            return true;
        }
    }

    public static boolean openChest(Player p, Block b) {
        if (!canOpenTheChest(p, b)) {
            p.sendMessage(Main.getPrefix() + " §cDu kannst die Kiste nicht auf machen, da ein Spieler sie gesichert hat.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            //Event Cancel = true
            return true;
        }
        return false;
    }

    public static boolean canOpenTheChest(Player p, Block b) {
        if (!ifChestProtected(b)) {
            return true;
        } else {
            if (!protectedChests.get(getChestLocation(b)).equalsIgnoreCase(p.getUniqueId().toString())) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean breakChest(Player p, Block b) {
        if (!canOpenTheChest(p, b)) {
            if (!p.isOp()) {
                p.sendMessage(Main.getPrefix() + " §cDu kannst die Kiste nicht abbauen, da ein Spieler sie gesichert hat.");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                //Event Cancel = true
                return true;
            } else {
                p.sendMessage(Main.getPrefix() + " §eDu bist nicht der Inhaber dieser Kiste, konntest sie aber trotzdem Abbauen, da du Server rechte hast!");
                p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1f, 1f);
                protectedChests.remove(getChestLocation(b));
                return false;
            }
        }
        if (ifChestProtected(b) && protectedChests.get(getChestLocation(b)).equalsIgnoreCase(p.getUniqueId().toString())) {
            protectedChests.remove(getChestLocation(b));
            p.sendMessage(Main.getPrefix() + " §7Du hast eine von dir gesicherte Kiste abgebaut. Die Sicherung wurde aufgehoben.");
            p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1f, 1f);
        }
        return false;
    }

    public static boolean ifChestProtected(Block b) {
        return protectedChests.containsKey(getChestLocation(b));
    }

    public static String getChestLocation(Block b) {
        return "Chest-" + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ();
    }

    public static void loadProtectedChestsFromFile() {
        try {
            File directory = new File(Main.getPluginPath() + "StreamSMP/");
            if (!directory.exists()) {
                directory.mkdir();
                File chestsFile = new File(Main.getPluginPath() + "StreamSMP/chests.txt");
                try {
                    chestsFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Lade Gesicherte Kisten.");
                File chestsFile = new File(Main.getPluginPath() + "StreamSMP/chests.txt");
                Scanner sc = new Scanner(chestsFile);
                String hashString = sc.nextLine();
                HashMap protectedChests1 = new Gson().fromJson(hashString, HashMap.class);
                protectedChests.putAll(protectedChests1);
                System.out.println(protectedChests.size() + " Kisten wurden geladen.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveProtectedChestsToFile() {
        if (!protectedChests.isEmpty()) {
            try {
                FileWriter chestsFile = new FileWriter(Main.getPluginPath() + "StreamSMP/chests.txt");
                chestsFile.write(protectedChests.toString());
                chestsFile.close();
                System.out.println(protectedChests.size() + " Kisten wurden gespeichert.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void startAutosaveProtectedChests() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInsatnce(), new Runnable() {
            @Override
            public void run() {
                saveProtectedChestsToFile();
            }
        }, 0L, 6000L);
    }
}
