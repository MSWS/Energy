package xyz.msws.energy.penalties.penalties;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.msws.energy.EnergyPlugin;

public class MovementPenalty extends EnergyPenalty {

    public MovementPenalty(EnergyPlugin plugin) {
        super(plugin);
        this.min = 0;
        this.max = .8;
    }

    @Override
    void apply(Entity ent) {
        if (!(ent instanceof Player))
            return;
        Player player = (Player) ent;
        double m = plugin.getTracker().getPlayer(player).getEnergy() / plugin.getEConfig().getMax();
        player.setWalkSpeed(0.2f * getSpeed(m));
    }

    @Override
    void remove(Entity ent) {
        if (!(ent instanceof Player))
            return;
        Player player = (Player) ent;
        player.setWalkSpeed(.2f);
    }

    @Override
    String getDescription(double prog) {
        return String.format("%d%% Movement Speed", (int) getSpeed(prog) * 100);
    }

    private float getSpeed(double prog) {
        float spd = 1;

        if (prog < .8)
            spd = .9f;
        if (prog < .5)
            spd = .8f;
        return spd;
    }
}
