package xyz.msws.energy.penalties.penalties;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import xyz.msws.energy.EnergyPlugin;

public class MovementPenalty extends EnergyPenalty {

    public MovementPenalty(EnergyPlugin plugin) {
        super(plugin);
        this.min = 0;
        this.max = .8;
    }

    @Override
    public void apply(LivingEntity ent, double energy) {
        if (!(ent instanceof Player))
            return;
        Player player = (Player) ent;
        player.setWalkSpeed(0.2f * getSpeed(energy));
    }

    @Override
    public void remove(LivingEntity ent) {
        if (!(ent instanceof Player))
            return;
        Player player = (Player) ent;
        player.setWalkSpeed(.2f);
    }

    @Override
    public boolean update(LivingEntity ent, double old, double now) {
        if (getSpeed(old) == getSpeed(now))
            return false;
        apply(ent, now);
        return true;
    }

    @Override
    public String getDescription(double prog) {
        return String.format("%d%% Movement Speed", (int) Math.round(getSpeed(prog) * 100.0));
    }

    private float getSpeed(double prog) {
        float spd = 1;

        if (prog < .8)
            spd = .9f;
        if (prog < .5)
            spd = .8f;
        if (prog < .3)
            spd = .5f;
        return spd;
    }
}
