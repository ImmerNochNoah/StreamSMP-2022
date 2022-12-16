package net.senoxplays.listeners;

import net.senoxplays.Main;
import net.senoxplays.backup.BackupSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class OnPlayerLoginEvent implements Listener {

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        if (BackupSystem.creatingABackup) {
            event.getPlayer().kickPlayer("§cServer macht gerade ein Backup!");
        }
        if (Main.maintenance) {
            if (!event.getPlayer().isOp()) {
                event.getPlayer().kickPlayer("§cDer SMP Server ist gerade in Wartung!");
            }
        }
    }
}
