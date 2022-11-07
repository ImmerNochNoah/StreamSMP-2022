package net.senoxplays.sleep;

import net.senoxplays.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sleep {

    public static ArrayList<Player> sleepingPlayer = new ArrayList<>();

    public static void enterBed(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isSleeping()) {
                    int op = Bukkit.getOnlinePlayers().size();
                    if (op == 1) {
                        skipNight();
                        return;
                    }

                    int opx2 = op / 2;
                    if (opx2 <= sleepingPlayer.size()) {
                        skipNight();
                    } else {
                        if (sleepingPlayer.size() == 0) {
                            Bukkit.getOnlinePlayers().forEach(all -> {
                                all.sendMessage(Main.getPrefix() + " " + Main.getPlayerPrefix(p) + "§f" + p.getName() + " §7möchte Schlafen!");
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f ,1f);
                            });
                        }
                        sleepingPlayer.add(p);

                        Bukkit.getOnlinePlayers().forEach(all -> {
                            all.sendMessage(Main.getPrefix() + " §aSchlafen: §8[§e" + sleepingPlayer.size() + " §7/ §e" + opx2 + "§8]");
                        });

                        if (opx2 <= sleepingPlayer.size()) {
                            skipNight();
                        }
                    }
                }
            }
        }.runTaskLater(Main.getInsatnce(), 5);
    }

    public static void skipNight() {
        for(World worlds : Bukkit.getWorlds()){
            worlds.setTime(200);
        }
        if (sleepingPlayer.size() > 0) {
            sleepingPlayer.clear();
        }
        Bukkit.getOnlinePlayers().forEach(all -> {
            all.sendMessage("§aDie Nacht wurde übersprungen!");
        });
    }

    public static void quitBed(Player p) {
        if (sleepingPlayer.contains(p)) {
            sleepingPlayer.remove(p);
        }
    }
}
