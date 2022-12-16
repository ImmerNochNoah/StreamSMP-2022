package net.senoxplays;

import net.senoxplays.beacon_chunkloader.BeaconChunkloader;
import net.senoxplays.chest_protection.ChestProtection;
import net.senoxplays.commands.*;
import net.senoxplays.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import static net.senoxplays.backup.BackupSystem.startAutoBackUp;

public class Main extends JavaPlugin {

    private static Main instance;
    public static String getPrefix() {
        return "§d§lINN.de §8»";
    }
    public static String getVersion() {
        return "0.1.1-alpha";
    }
    public static HashMap<String, String> playerPrefix = new HashMap<>();
    public static Boolean maintenance = false;

    @Override
    public void onEnable() {

        instance = this;
        Bukkit.getPluginManager().registerEvents(new OnPlayerChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoinQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerBedEnterEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerBedLeaveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerBreakBlockEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerBlockPlaceEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnBlockDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnVehicleMoveEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnVehicleCreateEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnWeatherChangeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerLoginEvent(), this);

        getCommand("prefix").setExecutor(new PrefixCmd());
        getCommand("kill").setExecutor(new KillCmd());
        getCommand("vote").setExecutor(new VoteCmd());
        getCommand("lock").setExecutor(new LockCmd());
        getCommand("backup").setExecutor(new BackupCmd());
        getCommand("loadchunks").setExecutor(new LoadChunksCmd());
        getCommand("wartung").setExecutor(new WartungCmd());

        ChestProtection.loadProtectedChestsFromFile();
        ChestProtection.startAutosaveProtectedChests();

        //DO NOT USE startBeaconChunkloader() TWICE!!!!!!!
        BeaconChunkloader.startBeaconChunkloader();

        serverRestart();
        startAutoBackUp();
    }

    @Override
    public void onDisable() {
        ChestProtection.saveProtectedChestsToFile();
        BeaconChunkloader.saveChunklistToFile();
    }

    public static Main getInsatnce() {
        return instance;
    }

    public static String getPlayerPrefix(Player p) {
        if (playerPrefix.containsKey(p.getUniqueId().toString())) {
            return playerPrefix.get(p.getUniqueId().toString()) + " ";
        } else {
            return " ";
        }
    }

    private void serverRestart() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            String time;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            time = simpleDateFormat.format(new Date());

            if (time.equalsIgnoreCase("00:00:00")) {
                Main.playerPrefix.clear();
                Bukkit.getServer().shutdown();
            }
        }, 0, 20);
    }

    public static String getPluginPath() {
        return System.getProperty("user.dir")+ "/plugins/";
    }

    public static String getServerPath() {
        return System.getProperty("user.dir") + "/";
    }

    public static void sendGlobalMessage(String msg) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.sendMessage(msg);
        });
    }

    public static void sendGlobalMessageSound(String msg, Sound sound) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.sendMessage(msg);
            onlinePlayer.playSound(onlinePlayer.getLocation(), sound, 1f, 1f);
        });
    }
}
