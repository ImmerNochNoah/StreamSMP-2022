package net.senoxplays.commands;

import net.senoxplays.Main;
import net.senoxplays.chest_protection.ChestProtection;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        Block b = p.getTargetBlock(null, 5);
        if (args.length == 0) {
            p.sendMessage(Main.getPrefix() + " §e/lock chest §7Sichere deine §4Redstone §7Kiste");
            p.sendMessage(Main.getPrefix() + " §e/lock remove §7Entferne die Sicherung von der Kiste.");
            p.sendMessage(Main.getPrefix() + " \n§6Stats: " + ChestProtection.protectedChestsSize() + " Kisten sind gerade gesichert.");
        } else {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("chest")) {
                    ChestProtection.protectChest(p, b);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    ChestProtection.removeProtectedChest(p, b);
                }
            }
        }
        return false;
    }
}
