package xyz.msws.energy.penalties.penalties;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyTracker;

import java.util.UUID;

public abstract class EnergyPenalty {
    protected double min, max;
    protected EnergyPlugin plugin;
    protected EnergyTracker tracker;

    public EnergyPenalty(EnergyPlugin plugin) {
        this.plugin = plugin;
        this.tracker = plugin.getTracker();
    }

    public void apply(UUID uuid) {
        Entity ent = Bukkit.getEntity(uuid);
        if (ent == null)
            return;
        apply(ent);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    abstract void apply(Entity ent);

    abstract void remove(Entity uuid);

    abstract String getDescription(double prog);
}
