package xyz.msws.energy.representation.messengers;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelledBossBarMessenger implements Messenger {

    private Map<UUID, BossBar> bars = new HashMap<>();

    @Override
    public void setEnergy(Player player, double level, double max) {
        int l = (int) Math.floor(level);
        String title = String.format("Energy: %d/%d", l, (int) max);
        BossBar bar = bars.getOrDefault(player.getUniqueId(), Bukkit.createBossBar(title, BarColor.GREEN, BarStyle.SEGMENTED_20));
        bars.put(player.getUniqueId(), bar);
        double prog = ((level % 1) / max) * 100;
        bar.setProgress(prog);
        bar.setVisible(true);
        bar.addPlayer(player);
        bar.setTitle(title);
    }
}
