package net.senoxplays.vote;

import net.senoxplays.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Weather {

    public static ArrayList<Player> weatherVotes = new ArrayList<>();

    public static void addWeatherVote(Player p) {
        if (!weatherVotes.contains(p)) {
            weatherVotes.add(p);
            int op = Bukkit.getOnlinePlayers().size();
            if (op == 1) {
                clearWeather();
                return;
            }
            int opx2 = op / 2;
            Bukkit.getOnlinePlayers().forEach(all -> {
                all.sendMessage(Main.getPrefix() + " " + Main.getPlayerPrefix(p) + "§f" + p.getName() + " §7ist dafür, dass das Wetter gecleart wird. §e/vote");
                all.sendMessage(Main.getPrefix() + " §8[§e" + weatherVotes.size() + " §7/ §e" + opx2 + "§8]");
            });

            if (opx2 == weatherVotes.size()) {
                clearWeather();
            }
        } else {
            p.sendMessage(Main.getPrefix() + " §7Du kannst nicht mehrere Male abstimmen.");
        }
    }

    public static void clearWeather() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear 900");
        Bukkit.getOnlinePlayers().forEach(all -> {
            all.sendMessage(Main.getPrefix() + " §a15 Minuten wurde der Regen ausgestellt.");
        });
        weatherVotes.clear();
    }
}
