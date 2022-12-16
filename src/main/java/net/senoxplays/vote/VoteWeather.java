package net.senoxplays.vote;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.senoxplays.Main;
import net.senoxplays.weather_system.Weather;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoteWeather {

    public static ArrayList<Player> weatherVotes = new ArrayList<>();
    private static Map<String, Long> cooldown = new HashMap<String, Long>();

    public static void addWeatherVote(Player p) {
        if (!weatherVotes.contains(p)) {

            if (!isRaining()) {
                p.sendMessage(Main.getPrefix() + " §7Es Regnet gerade nicht.");
                return;
            }

            if (cooldown.containsKey(p.getName())) {
                if (cooldown.get(p.getName()) > System.currentTimeMillis()) {
                    long timeleft = (cooldown.get(p.getName()) - System.currentTimeMillis()) / 1000;
                    p.sendMessage(Main.getPrefix() + " §7Bitte warte noch §e" + timeleft + " §7Sekunde(n).");
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                    return;
                }
            }
            cooldown.put(p.getName(), System.currentTimeMillis() + (600 * 1000));

            weatherVotes.add(p);
            int op = Bukkit.getOnlinePlayers().size();
            if (op == 1) {
                clearWeather();
                return;
            }

            int opx2 = op / 2;
            if (weatherVotes.size() == 1) {
                sendWeatherVoiceMessage(p);
            } else {
                Main.sendGlobalMessage(Main.getPrefix() + " §6Vote: §8[§e" + weatherVotes.size() + " §7/ §e" + opx2 + "§8]");
            }

            if (opx2 == weatherVotes.size()) {
                clearWeather();
            }
        } else {
            if (Bukkit.getOnlinePlayers().size() != 1) {
                p.sendMessage(Main.getPrefix() + " §7Du kannst nicht mehrere Male abstimmen.");
            } else {
                clearWeather();
            }
        }
    }

    public static void clearWeather() {
        Main.sendGlobalMessage(Main.getPrefix() + " §aEs Regnet nun nicht mehr.");
        Weather weather = new Weather();
        weather.setWeatherClear(900);
        weatherVotes.clear();
    }

    public static Boolean isRaining() {
        World world = Bukkit.getWorld("world");
        return world.hasStorm();
    }

    private static void sendWeatherVoiceMessage(Player p) {
        TextComponent tc = new TextComponent();
        Bukkit.getOnlinePlayers().forEach(all -> {
            tc.setText(Main.getPrefix() + " " + Main.getPlayerPrefix(p) + "§f" + p.getName() + " §7ist dafür, dass das Wetter gecleart wird. \n    §8[§aKLICKE HIER UM DAFÜR ZU VOTEN§8]");
            tc.setBold(true);
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote weather clear"));
            all.spigot().sendMessage(tc);
        });
    }
}
