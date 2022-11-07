package net.senoxplays.commands;

import net.senoxplays.Main;
import net.senoxplays.vote.Weather;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VoteCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        if (args.length == 0) {
            p.sendMessage(Main.getPrefix() + " §e/vote weather clear §7Entfernt Regen für 15 Minuten. \n");
            p.sendMessage(Main.getPrefix() + " §7Mehr Votes werden bald noch hinzugefügt.");
        } else {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("weather")) {
                    if (args[1].equalsIgnoreCase("clear")) {
                        Weather.addWeatherVote(p);
                    }
                }
            }
        }
        return false;
    }
}
