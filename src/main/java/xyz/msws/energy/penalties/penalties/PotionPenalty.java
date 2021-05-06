package xyz.msws.energy.penalties.penalties;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.data.PotionPenaltyData;

public class PotionPenalty extends EnergyPenalty {

    private final PotionPenaltyData data;

    public PotionPenalty(EnergyPlugin plugin, PotionPenaltyData data) {
        super(plugin);
        this.data = data.load(false);
        this.max = data.getMax() / plugin.getEConfig().getMax();
        this.min = data.getMin() / plugin.getEConfig().getMax();
    }

    @Override
    public void apply(LivingEntity ent, double energy) {
        data.apply(ent, energy);
    }

    @Override
    public void remove(LivingEntity ent) {
        data.remove(ent);
    }

    @Override
    public boolean update(LivingEntity ent, double old, double now) {
        if (data.getEffect(old).equals(data.getEffect(now)))
            return false;
        remove(ent);
        apply(ent, now);
        return true;
    }

    @Override
    public String getDescription(double prog) {
        PotionEffect eff = data.getEffect(prog);
        if (eff == null)
            return "None";
        return data.getEffect(prog).getType().getName() + " " + data.getEffect(prog).getAmplifier();
    }
}
