package xyz.msws.energy.chargers;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyTracker;

public abstract class Charger implements Listener {
    protected EnergyPlugin plugin;
    protected EnergyTracker tracker;

    public Charger(EnergyPlugin plugin) {
        this.plugin = plugin;
        this.tracker = plugin.getTracker();
    }

    public abstract String getDescription();

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public abstract void unregister();

}
