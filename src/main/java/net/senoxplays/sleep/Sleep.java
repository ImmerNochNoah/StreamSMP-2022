package net.senoxplays.sleep;

import net.senoxplays.Main;
import net.senoxplays.weather_system.Weather;
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
    static Map<String, Long> cooldown = new HashMap<String, Long>();

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
                            sendSleepMessage(p);
                        }
                        sleepingPlayer.add(p);
                        Main.sendGlobalMessage(Main.getPrefix() + " §aSchlafen: §8[§e" + sleepingPlayer.size() + " §7/ §e" + opx2 + "§8]");
                        if (opx2 <= sleepingPlayer.size()) {
                            skipNight();
                        }
                    }
                }
            }
        }.runTaskLater(Main.getInsatnce(), 5);
    }

    public static void skipNight() {
        Bukkit.getWorld("world").setTime(200);
        Weather weather = new Weather();
        weather.setWeatherClear(300);
        if (sleepingPlayer.size() > 0) {
            sleepingPlayer.clear();
        }
        Main.sendGlobalMessage("§aDie Nacht wurde übersprungen!");
    }

    public static void quitBed(Player p) {
        if (sleepingPlayer.contains(p)) {
            sleepingPlayer.remove(p);
        }
    }

    private static void sendSleepMessage(Player p) {
        if (!cooldown.containsKey("sleep_message_send")) {
            Main.sendGlobalMessageSound(Main.getPrefix() + " " + Main.getPlayerPrefix(p) + "§f" + p.getName() + " §7möchte Schlafen!", Sound.BLOCK_NOTE_BLOCK_PLING);
            cooldown.put("sleep_message_send", System.currentTimeMillis() + (10 * 1000));
        } else {
            if (cooldown.get("sleep_message_send") < System.currentTimeMillis()) {
                cooldown.clear();

                sendSleepMessage(p);
            }
        }
    }
}
