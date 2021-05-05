package xyz.msws.energy.penalties.penalties;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.msws.energy.EnergyPlugin;

public class HungerPenalty extends EnergyPenalty {
    public HungerPenalty(EnergyPlugin plugin) {
        super(plugin);
        min = 0;
        max = .5;
    }

    @Override
    public void apply(Entity ent) {
        if (!(ent instanceof LivingEntity))
            return;
        LivingEntity player = (LivingEntity) ent;
        int level = getHungerLevel(plugin.getTracker().getPlayer(player).getEnergy() / plugin.getEConfig().getMax());
        if (level == 0)
            return;

        PotionEffect eff = new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, level - 1);
        player.addPotionEffect(eff);
    }

    @Override
    public void remove(Entity ent) {
        if (!(ent instanceof LivingEntity))
            return;
        LivingEntity player = (LivingEntity) ent;
        player.removePotionEffect(PotionEffectType.HUNGER);
    }

    @Override
    public boolean update(Entity ent, double old, double now) {
        return getHungerLevel(old) != getHungerLevel(now);
    }

    @Override
    public String getDescription(double prog) {
        return "Hunger " + getHungerLevel(prog);
    }

    private int getHungerLevel(double prog) {
        int level = 0;
        if (prog <= .5)
            level = 1;
        if (prog < .2)
            level = 2;
        return level;
    }
}
