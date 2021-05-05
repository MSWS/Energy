package xyz.msws.energy.data;

import org.bukkit.configuration.file.YamlConfiguration;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.penalties.costs.CostType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreConfig extends EnergyConfig {

    private File file;
    private YamlConfiguration config;

    public CoreConfig(EnergyPlugin plugin, File file) {
        super(plugin);
        this.file = file;
    }

    @Override
    public void load() {
        if (!file.exists()) {
            config = new YamlConfiguration();
            setDefaults();
            save();
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        this.max = config.getDouble("MaxEnergy", 100);
        this.defEnergy = config.getDouble("DefaultEnergy", 100);
        List<String> costStrings = config.getStringList("Costs");
        costStrings.forEach(c -> costs.add(CostType.fromString(c)));
    }

    @Override
    public void save() {
        config.set("MaxEnergy", max);
        config.set("DefaultEnergy", defEnergy);
        List<String> cs = new ArrayList<>();
        costs.forEach(c -> cs.add(c.toString()));
        config.set("Costs", cs);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDefaults() {
        this.max = 100;
        this.defEnergy = 100;
        this.costs = new ArrayList<>(Arrays.asList(CostType.values()));
    }
}
