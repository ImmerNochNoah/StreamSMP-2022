package net.senoxplays.tablist;

import net.senoxplays.Main;
import org.bukkit.entity.Player;

public class Tablist {

    public static void setTablist(Player p) {
        p.setPlayerListHeaderFooter("\n§b§lStreamSMP\n", "\n§7immernochnoah.de \n§e" + Main.getVersion() + "\n ");
    }
}
