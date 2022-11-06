package net.senoxplays.listeners;

import net.senoxplays.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class OnPlayerChatEvent implements Listener {

   @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
       event.setFormat(Main.getPlayerPrefix(p) +"§f<" + p.getName() + "> " + event.getMessage());
    }
}
