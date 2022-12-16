package net.senoxplays.commands;

import net.senoxplays.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WartungCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player) commandSender;
        if (p.isOp()) {
            if (!Main.maintenance) {
                p.sendMessage(Main.getPrefix() + " §eServer ist nun in Wartung!");
                Main.maintenance = true;
            } else {
                p.sendMessage(Main.getPrefix() + " §aServer ist nun nicht mehr in Wartung!");
                Main.maintenance = true;
            }
        }
        return false;
    }
}
