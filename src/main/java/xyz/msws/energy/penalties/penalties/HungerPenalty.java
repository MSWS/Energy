package xyz.msws.energy.penalties.penalties;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.msws.energy.EnergyPlugin;

public class HungerPenalty extends EnergyPenalty {
    public HungerPenalty(EnergyPlugin plugin) {
        super(plugin);
        min = 0;
        max = .6;
    }

    @Override
    public void apply(LivingEntity ent, double energy) {
        int level = getHungerLevel(energy);
        if (level == 0)
            return;

        PotionEffect eff = new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, level - 1);
        ent.addPotionEffect(eff);
    }

    @Override
    public void remove(LivingEntity ent) {
        ent.removePotionEffect(PotionEffectType.HUNGER);
    }

    @Override
    public boolean update(LivingEntity ent, double old, double now) {
        if (getHungerLevel(old) == getHungerLevel(now))
            return false;
        remove(ent);
        apply(ent, now);
        return true;
    }

    @Override
    public String getDescription(double prog) {
        return "Hunger " + getHungerLevel(prog);
    }

    private int getHungerLevel(double prog) {
        int level = 0;
        if (prog <= .6)
            level = 1;
        if (prog < .5)
            level = 2;
        if (prog < .3)
            level = 3;
        if (prog < .2)
            level = 4;
        if (prog < .1)
            level = 5;
        return level;
    }
}
