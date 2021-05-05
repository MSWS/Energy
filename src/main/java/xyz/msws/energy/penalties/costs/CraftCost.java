package xyz.msws.energy.penalties.costs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyModifier;

public class CraftCost extends EnergyModifier {

    public CraftCost(EnergyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getDescription() {
        return "Crafting";
    }

    @Override
    public void unregister() {
        CraftItemEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        tracker.getPlayer(player).modEnergy(this, CostType.CRAFTING.getCost());
    }
}
