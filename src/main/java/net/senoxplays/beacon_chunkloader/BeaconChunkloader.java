package net.senoxplays.beacon_chunkloader;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.IntegerValue;
import net.senoxplays.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.*;

public class BeaconChunkloader {
    static ArrayList<Chunk> loadedChunks = new ArrayList<>();

    //For Player Side
    public static void createBeacon(Player player, Block block) {
        if (loadedChunks.size() >= 25) {
            player.sendMessage(Main.getPrefix() + " §cMaximal 25 Chunkloader dürfen gleichzeitig laufen!");
        } else {
            Location beaconLoc = block.getLocation();
            Chunk loadedChunk = beaconLoc.getChunk();

            //Save's the Chunk in the ArrayList
            addToLoadedChunks(loadedChunk);

            //Load the Chunk
            if (!loadedChunk.isForceLoaded()) {
                player.sendMessage(Main.getPrefix() +" §aDieser Chunk ist nun solange geladen bis der Beacon abgebaut wird.");
            }
        }
    }

    public static void removeBeacon(Player player, Block block) {
        Location beaconLoc = block.getLocation();
        Chunk loadedChunk = beaconLoc.getChunk();
        World beaconWorld = Bukkit.getWorld(beaconLoc.getWorld().getName());

        //Remove's the Chunk in the ArrayList
        removeFromLoadedChunks(loadedChunk);

        //unload's the Chunk
        if (loadedChunk.isForceLoaded()) {
            beaconWorld.setChunkForceLoaded(loadedChunk.getX(), loadedChunk.getZ(), false);
            player.sendMessage(Main.getPrefix() +" §cDieser Chunk wird nun nicht mehr geladen.");
        }
    }

    public static void checkIfAllChunksAreLoadedFromCmd(Player player) {
        int loadedChunksInt = 0;
        if (loadedChunks.size() != 0) {
            for (int i = 0; i < loadedChunks.size(); i++) {
                Chunk beaconChunk = loadedChunks.get(i);
                if (!beaconChunk.isLoaded()) {
                    player.sendMessage("§c" + beaconChunk + " §8| §7nicht geladen.");
                    loadChunkFromCmd(player, beaconChunk);
                } else {
                    player.sendMessage("§a" + beaconChunk + " §8| §7geladen.");
                    loadedChunksInt++;
                }
            }
            player.sendMessage(Main.getPrefix() + " §a" + loadedChunksInt + " §7Chunks sind geladen.");
        } else {
            player.sendMessage(Main.getPrefix() + " §7Momentan gibt es keine gespeicherten Chunks");
        }
    }

    public static void loadChunkFromCmd(Player player, Chunk chunk) {
        player.sendMessage("§aProbiere §e" + chunk + " §azu laden...");

        if (chunk.isForceLoaded()) {
            if (!chunk.isLoaded()) {
                chunk.load();
            }
        } else {
            chunk.setForceLoaded(true);
        }

        if (!chunk.isLoaded()) {
            player.sendMessage("§c" + chunk + " §7konnte nicht geladen werden...");
        } else {
            player.sendMessage("§a" + chunk + " §7wurde geladen!");
        }
    }

    //Console Side

    public static void checkIfAllChunksAreLoaded() {
        int loadedChunksInt = 0;
        if (loadedChunks.size() != 0) {
            for (int i = 0; i < loadedChunks.size(); i++) {
                Chunk beaconChunk = loadedChunks.get(i);
                if (!beaconChunk.isLoaded()) {
                    loadChunk(beaconChunk);
                } else {
                    loadedChunksInt++;
                }
            }
            System.out.println(loadedChunksInt + " Chunks sind geladen.");
        } else {
            System.out.println("Momentan sind keine Chunks in der Chunkliste");
        }
    }

    public static void loadChunk(Chunk chunk) {
        System.out.println("Probiere " + chunk + " zu laden...");

        if (chunk.isForceLoaded()) {
            if (!chunk.isLoaded()) {
                chunk.load();
            }
        } else {
            chunk.setForceLoaded(true);
        }

        if (!chunk.isLoaded()) {
            System.out.println(chunk + " konnte nicht geladen werden...");
        } else {
            System.out.println(chunk + " wurde geladen!");
        }
    }

    public static void addToLoadedChunks(Chunk chunk) {
        if (!loadedChunks.contains(chunk)) {
            loadedChunks.add(chunk);
        }
    }

    public static void removeFromLoadedChunks(Chunk chunk) {
        loadedChunks.remove(chunk);
    }

    public static void loadChunklistFromFile() {
        try {
            File directory = new File(Main.getPluginPath() + "StreamSMP/LoadedChunks/");
            if (!directory.exists()) {
                directory.mkdir();
                File loadedChunksXFile = new File(Main.getPluginPath() + "StreamSMP/LoadedChunks/loadedchunksX.json");
                File loadedChunksZFile = new File(Main.getPluginPath() + "StreamSMP/LoadedChunks/loadedchunksZ.json");

                loadedChunksXFile.createNewFile();
                loadedChunksZFile.createNewFile();

            } else {
                File loadedChunksXFile = new File(Main.getPluginPath() + "StreamSMP/LoadedChunks/loadedchunksX.json");
                File loadedChunksZFile = new File(Main.getPluginPath() + "StreamSMP/LoadedChunks/loadedchunksZ.json");

                if (!loadedChunksXFile.exists()) {
                    loadedChunksXFile.createNewFile();
                    loadedChunksZFile.createNewFile();
                }

                Scanner scX = new Scanner(loadedChunksXFile);
                Scanner scZ = new Scanner(loadedChunksZFile);

                if (!scX.hasNextLine() && !scZ.hasNextLine()) {
                    System.out.println("Es wurden keine geladenen Chunks in der loadedchunksX.json & loadedchunksZ.json File gefunden.");
                } else {
                    String arrayStringX = scX.nextLine();
                    String arrayStringZ = scZ.nextLine();

                    ArrayList loadedChunksX1 = new ArrayList<>(Arrays.asList(arrayStringX.split("\\s*,\\s*")));
                    ArrayList loadedChunksZ1 = new ArrayList<>(Arrays.asList(arrayStringZ.split("\\s*,\\s*")));

                    for (int i = 0; i < loadedChunksX1.size(); i++) {

                        int x = Integer.parseInt(String.valueOf(loadedChunksX1.get(i)).replace("[", "").replace("]", ""));
                        int z = Integer.parseInt(String.valueOf(loadedChunksZ1.get(i)).replace("[", "").replace("]", ""));

                        Chunk chunk = Bukkit.getWorld("world").getChunkAt(x, z);
                        loadedChunks.add(chunk);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveChunklistToFile() {
        if (!loadedChunks.isEmpty()) {
            try {
                FileWriter loadedChunksXFile = new FileWriter(Main.getPluginPath() + "StreamSMP/LoadedChunks/loadedchunksX.json");
                FileWriter loadedChunksZFile = new FileWriter(Main.getPluginPath() + "StreamSMP/LoadedChunks/loadedchunksZ.json");

                ArrayList<String> loadedChunksX = new ArrayList<>();
                ArrayList<String> loadedChunksZ = new ArrayList<>();

                for (int i = 0; i < loadedChunks.size(); i++) {
                    loadedChunksX.add(loadedChunks.get(i).getX() + "");
                    loadedChunksZ.add(loadedChunks.get(i).getZ() + "");
                }

                loadedChunksXFile.write(loadedChunksX.toString());
                loadedChunksXFile.close();

                loadedChunksZFile.write(loadedChunksZ.toString());
                loadedChunksZFile.close();

                loadedChunksX.clear();
                loadedChunksZ.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //This is used to Start the BeaconChunkloader! DO NOT USE THIS TWICE!!!!!
    public static void startBeaconChunkloader() {
        BeaconChunkloader.loadChunklistFromFile();
        BeaconChunkloader.startAutosaveBeaconChunks();
        BeaconChunkloader.checkIfAllChunksAreLoaded();
    }

    public static void startAutosaveBeaconChunks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInsatnce(), new Runnable() {
            @Override
            public void run() {
                saveChunklistToFile();
                BeaconChunkloader.checkIfAllChunksAreLoaded();
            }
        }, 0L, 6000L);
    }
}
