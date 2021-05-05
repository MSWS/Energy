package xyz.msws.energy.trackers;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import xyz.msws.energy.EnergyPlugin;

public abstract class EnergyModifier implements Listener {
    protected EnergyPlugin plugin;
    protected EnergyTracker tracker;

    public EnergyModifier(EnergyPlugin plugin) {
        this.plugin = plugin;
        this.tracker = plugin.getTracker();
    }

    public abstract String getDescription();

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public abstract void unregister();
}
