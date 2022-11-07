package net.senoxplays;

import net.senoxplays.commands.KillCmd;
import net.senoxplays.commands.PrefixCmd;
import net.senoxplays.commands.VoteCmd;
import net.senoxplays.listeners.*;
import net.senoxplays.prefix.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Main extends JavaPlugin {

    private static Main instance;
    public static String getPrefix() {
        return "§d§lINN.de §8»";
    }

    public static String getVersion() {
        return "0.0.2";
    }

    public static HashMap<String, String> playerPrefix = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new OnPlayerChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoinQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerBedEnterEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerBedLeaveEvent(), this);

        getCommand("prefix").setExecutor(new PrefixCmd());
        getCommand("kill").setExecutor(new KillCmd());
        getCommand("vote").setExecutor(new VoteCmd());

        serverRestart();

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

}
