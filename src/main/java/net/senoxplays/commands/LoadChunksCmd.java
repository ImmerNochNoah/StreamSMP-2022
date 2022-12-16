package net.senoxplays.commands;

import net.senoxplays.Main;
import net.senoxplays.backup.BackupSystem;
import net.senoxplays.beacon_chunkloader.BeaconChunkloader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoadChunksCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player) commandSender;
        if (!p.isOp()) {
            p.sendMessage(Main.getPrefix() + " §cDafür hast du keine Rechte");
        } else {
            p.sendMessage(Main.getPrefix() + " §aSchaue ob Chunks geladen sind:");
            BeaconChunkloader.checkIfAllChunksAreLoadedFromCmd(p);
        }
        return false;
    }
}
