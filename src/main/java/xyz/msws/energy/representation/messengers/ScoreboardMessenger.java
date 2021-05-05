package xyz.msws.energy.representation.messengers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardMessenger implements Messenger {

    private Map<UUID, Scoreboard> boards = new HashMap<>();

    @Override
    public void setEnergy(Player player, double level, double max) {
        String name = String.format("%.02f / %d", level, (int) max);
        if (!boards.containsKey(player.getUniqueId())) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective(name, "dummy", "exp");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            boards.put(player.getUniqueId(), board);
        }

        Scoreboard board = boards.get(player.getUniqueId());
        board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(name);
        player.setScoreboard(board);
    }
}
