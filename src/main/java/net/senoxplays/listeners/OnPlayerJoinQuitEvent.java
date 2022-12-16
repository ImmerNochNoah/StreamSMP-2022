package net.senoxplays.listeners;

import net.senoxplays.Main;
import net.senoxplays.prefix.Prefix;
import net.senoxplays.tablist.Tablist;
import net.senoxplays.vote.VoteWeather;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnPlayerJoinQuitEvent implements Listener {


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        event.setJoinMessage(null);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.hasPlayedBefore()) {
                    Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                        onlinePlayer.sendMessage(Main.getPlayerPrefix(p) + "§f" + p.getName() + " §eist zum ersten Mal hier!");
                    });
                } else {
                    Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                        onlinePlayer.sendMessage(Main.getPlayerPrefix(p) + "§f" + p.getName() + " §eist dem Server beigetreten.");
                    });
                }

                Tablist.setTablist(p);

                Prefix prefix = new Prefix();
                prefix.setPlayerTeams(p);

                if (Main.maintenance) {
                    p.sendMessage(Main.getPrefix() + "§8[§eACHTUNG§8] §7DER SERVER IST GERADE IN WARTUNG! §8[§eACHTUNG§8]");
                    p.sendMessage("§a/wartung §7um aus der Wartung zu gehen.");
                    p.sendMessage("§eBackups werden bei Wartungen nicht gemacht oder gespeichert!");
                }
            }
        }.runTaskLater(Main.getInsatnce(), 10);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        event.setQuitMessage(null);
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.sendMessage(Main.getPlayerPrefix(p) + "§f" + p.getName() + " §ehat den Server verlassen. :(");
        });

        VoteWeather.weatherVotes.remove(p);
    }
}
