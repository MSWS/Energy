package xyz.msws.energy.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.penalties.costs.CostType;
import xyz.msws.energy.penalties.penalties.PotionPenalty;
import xyz.msws.energy.utils.MSG;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoreConfig extends EnergyConfig {

    private final File file;
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
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        this.max = config.getDouble("MaxEnergy", 100);
        this.defEnergy = config.getDouble("DefaultEnergy", 100);
        this.ignoreCreative = config.getBoolean("IgnoreCreative", true);
        List<String> costStrings = config.getStringList("Costs");
        costStrings.forEach(c -> costs.add(CostType.fromString(c)));

        ConfigurationSection penalties = config.getConfigurationSection("Penalties");
        if (penalties == null) {
            MSG.log("[WARNING] No penalties were found, make sure config is properly formatted.");
            return;
        }

        ConfigurationSection potions = penalties.getConfigurationSection("Potions");

        if (potions != null) {
            for (String potion : potions.getKeys(false)) {
                PotionPenaltyData pd = new PotionPenaltyData(potions.getConfigurationSection(potion), potion);
                plugin.getTracker().addPenalty(new PotionPenalty(plugin, pd.load(false)));
            }
        }
    }

    @Override
    public void save() {
        config.set("MaxEnergy", max);
        config.set("DefaultEnergy", defEnergy);
        config.set("IgnoreCreative", ignoreCreative);
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
        plugin.saveResource("config.yml", true);
    }
}
