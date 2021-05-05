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

    public void apply(UUID uuid, double energy) {
        Entity ent = Bukkit.getEntity(uuid);
        if (ent == null)
            return;
        apply(ent, energy);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public abstract void apply(Entity ent, double energy);

    public boolean update(Entity ent, double old, double now) {
        return false;
    }

    public abstract void remove(Entity ent);

    public abstract String getDescription(double prog);
}
