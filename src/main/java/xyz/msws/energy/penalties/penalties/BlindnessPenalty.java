package xyz.msws.energy.penalties.penalties;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.msws.energy.EnergyPlugin;

public class BlindnessPenalty extends EnergyPenalty {
    public BlindnessPenalty(EnergyPlugin plugin) {
        super(plugin);
        min = 0;
        max = .1;
    }

    @Override
    public void apply(Entity ent, double energy) {
        if (!(ent instanceof LivingEntity))
            return;
        LivingEntity player = (LivingEntity) ent;
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
    }

    @Override
    public void remove(Entity ent) {
        if (!(ent instanceof LivingEntity))
            return;
        LivingEntity player = (LivingEntity) ent;
        player.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    @Override
    public String getDescription(double prog) {
        return "Blindness";
    }
}
