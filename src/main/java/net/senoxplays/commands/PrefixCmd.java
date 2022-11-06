package net.senoxplays.commands;

import net.senoxplays.Main;
import net.senoxplays.prefix.Prefix;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PrefixCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        if (args.length == 1) {
            if (args[0].length() < 15) {
                String pp = args[0];
                String playerPrefix = pp.replace("&", "§");
                p.sendMessage(playerPrefix);
                p.sendMessage(Main.getPrefix() + " §7Du hast dein Prefix zu §f" + playerPrefix +  " §7gesetzt.");
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.1f, 0.1f);

                Prefix prefix = new Prefix();
                prefix.setPlayerPrefix(p, playerPrefix);
            } else {
                p.sendMessage(Main.getPrefix() + " §cDein Prefix darf nicht länger als 15 Buchstaben sein.");
                p.playSound(p.getLocation(), Sound.ENTITY_CAT_HISS, 0.1f, 0.1f);
            }
        } else {
            p.sendMessage(Main.getPrefix() + " §7Bitte benutze §e/prefix <PREFIX> \n§7Dein Prefix darf nicht länger als 15 Buchstaben sein.");
        }
        return false;
    }
}
