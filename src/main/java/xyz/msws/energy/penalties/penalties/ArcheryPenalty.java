package xyz.msws.energy.penalties.penalties;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;
import xyz.msws.energy.EnergyPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ArcheryPenalty extends EnergyPenalty implements Listener {
    public ArcheryPenalty(EnergyPlugin plugin) {
        super(plugin);
        min = 0;
        max = .6;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final Map<UUID, Double> accuracies = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onShoot(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player))
            return;
        if (!(event.getEntity() instanceof Arrow))
            return;
        Arrow arrow = (Arrow) event.getEntity();
        Player player = (Player) event.getEntity().getShooter();
        if (!accuracies.containsKey(player.getUniqueId()))
            return;
        double acc = 1 - accuracies.get(player.getUniqueId());
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double ox = rnd.nextDouble(-acc / 2, acc / 2), oy = rnd.nextDouble(-acc / 2, acc / 2), oz = rnd.nextDouble(-acc / 2, acc / 2);

        Vector vec = arrow.getVelocity().clone().add(new Vector(ox, oy, oz));
        double len = arrow.getVelocity().clone().length();
        vec.normalize().multiply(len * (1 - acc));
        arrow.setVelocity(vec);
    }

    @Override
    public void apply(LivingEntity ent, double energy) {
        accuracies.put(ent.getUniqueId(), getAccuracy(energy));
    }

    @Override
    public void remove(LivingEntity ent) {
        accuracies.remove(ent.getUniqueId());
    }

    @Override
    public String getDescription(double prog) {
        return String.format("%d%% Archery Accuracy", (int) (getAccuracy(prog) * 100.0));
    }

    @Override
    public boolean update(LivingEntity ent, double old, double now) {
        if (getAccuracy(old) == getAccuracy(now))
            return false;
        apply(ent, now);
        return true;
    }

    private double getAccuracy(double prog) {
        double acc = 1;
        if (prog < .8)
            acc = .9;
        if (prog < .6)
            acc = .8;
        if (prog < .5)
            acc = .5;
        if (prog < .35)
            acc = .25;
        if (prog < .2)
            acc = .2;
        if (prog < .1)
            acc = .05;
        return acc;
    }
}
