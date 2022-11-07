package net.senoxplays.listeners;

import net.senoxplays.sleep.Sleep;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class OnPlayerBedLeaveEvent implements Listener {

    @EventHandler
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
        Player p = event.getPlayer();
        Sleep.quitBed(p);
    }
}
