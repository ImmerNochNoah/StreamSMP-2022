package net.senoxplays.listeners;

import net.senoxplays.sleep.Sleep;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class OnPlayerBedEnterEvent implements Listener {

    @EventHandler
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
        Player p = event.getPlayer();
        Sleep.enterBed(p);
    }
}
