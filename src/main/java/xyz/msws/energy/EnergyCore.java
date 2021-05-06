package xyz.msws.energy;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.msws.energy.commands.EnergyCommand;
import xyz.msws.energy.data.CoreConfig;
import xyz.msws.energy.data.EnergyConfig;
import xyz.msws.energy.trackers.CoreTracker;
import xyz.msws.energy.trackers.EnergyTracker;

import java.io.File;

public class EnergyCore extends JavaPlugin implements EnergyPlugin {
    private EnergyTracker tracker;
    private EnergyConfig config;

    @Override
    public void onEnable() {
        tracker = new CoreTracker(this);
        tracker.load();
        config = new CoreConfig(this, new File(getDataFolder(), "config.yml"));
        config.load();


        getCommand("energy").setExecutor(new EnergyCommand(this));
    }

    @Override
    public void onDisable() {
        tracker.unload();
    }

    @Override
    public EnergyTracker getTracker() {
        return tracker;
    }

    @Override
    public EnergyConfig getEConfig() {
        return config;
    }
}
