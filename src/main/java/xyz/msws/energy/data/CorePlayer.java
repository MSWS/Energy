package xyz.msws.energy.data;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.events.PlayerEnergyModifyEvent;
import xyz.msws.energy.events.PlayerEnergySetEvent;
import xyz.msws.energy.representation.Medium;
import xyz.msws.energy.representation.messengers.Messenger;
import xyz.msws.energy.trackers.EnergyModifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class CorePlayer extends EnergyPlayer {
    private final Map<Long, Double> bars = new HashMap<>();

    private final EnergyPlugin plugin;

    public CorePlayer(EnergyPlugin plugin, UUID uuid) {
        super(plugin.getEConfig(), uuid);
        this.plugin = plugin;
    }

    public CorePlayer(EnergyPlugin plugin, Entity player) {
        this(plugin, player.getUniqueId());
    }

    @Override
    public void modEnergy(EnergyModifier cost, double amo) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && plugin.getEConfig().getIgnoreCreative() && player.getGameMode() == GameMode.CREATIVE)
            return;

        PlayerEnergyModifyEvent event = new PlayerEnergyModifyEvent(this, cost, amo);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        amo = event.getChange();

        if (amo > 0) {
            long time = 0;
            double step = .01;
            for (double d = 0; d < amo; d += step) {
                double ep = (this.energy + amo + d) / plugin.getEConfig().getMax();
                bars.put(time, step);
                time += 10000.0 * step * ep;
            }

            new BukkitRunnable() {
                final long start = System.currentTimeMillis();

                @Override
                public void run() {
                    Iterator<Map.Entry<Long, Double>> it = bars.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Long, Double> add = it.next();
                        if (System.currentTimeMillis() - start < add.getKey())
                            continue;
                        CorePlayer.this.energy += add.getValue();
                        CorePlayer.this.energy = Math.max(Math.min(CorePlayer.this.energy, config.getMax()), 0);
                        updateMessenger();
                        it.remove();
                    }
                    if (bars.entrySet().isEmpty())
                        this.cancel();
                }
            }.runTaskTimer(plugin, 0, 1);
            return;
        }

        this.energy += amo;
        this.energy = Math.max(Math.min(this.energy, config.getMax()), 0);
        updateMessenger();
    }

    @Override
    public void setEnergy(double e) {
        PlayerEnergySetEvent event = new PlayerEnergySetEvent(this, e);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        e = event.getChange();
        super.setEnergy(e);
    }

    @Override
    public Messenger getMessenger() {
        return Medium.XP_LEVEL.getMessenger();
    }
}
