package net.senoxplays.listeners;

import net.senoxplays.Main;
import net.senoxplays.prefix.Prefix;
import net.senoxplays.tablist.Tablist;
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

                Tablist tablist = new Tablist();
                tablist.setTablist(p);

                Prefix prefix = new Prefix();
                prefix.setPlayerTeams(p);
                prefix.setAllPlayerTeams();
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
    }
}
