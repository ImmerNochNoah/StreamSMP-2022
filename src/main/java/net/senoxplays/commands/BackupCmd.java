package net.senoxplays.commands;

import net.senoxplays.Main;
import net.senoxplays.backup.BackupSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BackupCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player) commandSender;
        if (!p.isOp()) {
            p.sendMessage(Main.getPrefix() + " §cDafür hast du keine Rechte");
        } else {
            p.sendMessage(Main.getPrefix() + " §aStarte Backup:");
            BackupSystem.createBackup();
        }
        return false;
    }
}
