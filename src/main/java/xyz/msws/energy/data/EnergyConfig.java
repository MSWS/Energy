package xyz.msws.energy.data;

import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.penalties.costs.CostType;

import java.util.ArrayList;
import java.util.List;

public abstract class EnergyConfig {
    protected double max, defEnergy;
    protected List<CostType> costs = new ArrayList<>();
    protected EnergyPlugin plugin;
    protected boolean ignoreCreative;

    public EnergyConfig(EnergyPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void load();

    public abstract void save();

    public abstract void setDefaults();

    public double getMax() {
        return max;
    }

    public double getDefault() {
        return defEnergy;
    }

    public boolean getIgnoreCreative() {
        return ignoreCreative;
    }

    public void setIgnoreCreative(boolean ic) {
        this.ignoreCreative = ic;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setDefault(double def) {
        this.defEnergy = def;
    }

    public void setCost(CostType type, double cost) {
        type.setCost(cost);
    }

    public List<CostType> getCosts() {
        return costs;
    }
}
