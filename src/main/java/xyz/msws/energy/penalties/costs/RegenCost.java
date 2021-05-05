package xyz.msws.energy.penalties.costs;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyModifier;

public class RegenCost extends EnergyModifier {

    public RegenCost(EnergyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getDescription() {
        return "Regeneration";
    }

    @Override
    public void unregister() {
        EntityRegainHealthEvent.getHandlerList().unregister(this);
    }

    public void onRegen(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        tracker.getPlayer(player).modEnergy(this, -CostType.REGEN.getCost());
    }
}
