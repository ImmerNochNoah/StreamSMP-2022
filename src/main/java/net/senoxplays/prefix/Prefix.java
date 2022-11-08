package net.senoxplays.prefix;

import net.senoxplays.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Prefix {
    public static ScoreboardManager manager = Bukkit.getScoreboardManager();
    public static Scoreboard board = manager.getNewScoreboard();

    public void setPlayerPrefix(Player p, String prefix) {
        if (!Main.playerPrefix.containsKey(p)) {
            Main.playerPrefix.put(p.getUniqueId().toString(), prefix);
        } else {
            Main.playerPrefix.replace(p.getUniqueId().toString(), prefix);
        }
        setPlayerTeams(p);
    }

    public void setPlayerTeams(Player p) {
        Team team = board.getTeam(p.getName());
        if (team == null) {
            team = board.registerNewTeam(p.getName());
        }
        team.addPlayer(p);
        team.setPrefix(Main.getPlayerPrefix(p));
        updateTablistPrefix();
    }

    public void updateTablistPrefix() {
        for(Player online : Bukkit.getOnlinePlayers()){
            online.setScoreboard(board);
        }
    }
}
