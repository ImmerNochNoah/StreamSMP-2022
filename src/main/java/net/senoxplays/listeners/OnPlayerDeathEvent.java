package net.senoxplays.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player p = event.getEntity();

        if (p.getKiller() == null || p == p.getKiller()) {
            p.sendMessage("§cDeine Letzten Koordinaten.");
            int X = (int) p.getLocation().getX();
            int Y = (int) p.getLocation().getY();
            int Z = (int) p.getLocation().getZ();
            p.sendMessage("§eX §7" + X + " §eY §7" + Y + " §eZ §7" + Z);
            p.sendMessage(" §l");
        }
    }
}
