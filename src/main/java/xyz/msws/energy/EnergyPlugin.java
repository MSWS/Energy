package xyz.msws.energy;

import org.bukkit.plugin.Plugin;
import xyz.msws.energy.data.EnergyConfig;
import xyz.msws.energy.trackers.EnergyTracker;

public interface EnergyPlugin extends Plugin {
    EnergyTracker getTracker();
    EnergyConfig getEConfig();
}
