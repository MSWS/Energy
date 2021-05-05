package xyz.msws.energy.penalties.costs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyModifier;

public class BlockCost extends EnergyModifier {

    public BlockCost(EnergyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getDescription() {
        return "Break/Place Blocks";
    }

    @Override
    public void unregister() {
        BlockPlaceEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        tracker.getPlayer(player).modEnergy(this, -CostType.BLOCK_PLACE.getCost());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        tracker.getPlayer(player).modEnergy(this, -CostType.BLOCK_BREAK.getCost());
    }

}
