package xyz.msws.energy.penalties.costs;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyModifier;

public class CombatCost extends EnergyModifier {

    public CombatCost(EnergyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getDescription() {
        return "Combat";
    }

    @Override
    public void unregister() {
        EntityDamageEvent.getHandlerList().unregister(this);
        EntityDamageByEntityEvent.getHandlerList().unregister(this);
        ProjectileLaunchEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        tracker.getPlayer(player).modEnergy(this, CostType.HURT.getCost());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getDamager();
        tracker.getPlayer(player).modEnergy(this, CostType.DAMAGING.getCost());
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player))
            return;
        Player player = (Player) event.getEntity().getShooter();
        tracker.getPlayer(player).modEnergy(this, -((event.getEntity() instanceof Arrow) ? CostType.ARCHERY : CostType.THROWING).getCost());
    }
}
