package xyz.msws.energy.penalties.costs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyModifier;

public class SmeltCost extends EnergyModifier {
    public SmeltCost(EnergyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getDescription() {
        return "Smelting";
    }

    @Override
    public void unregister() {
        FurnaceBurnEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onBurn(InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;
        InventoryType type = event.getClickedInventory().getType();
        if (type != InventoryType.BLAST_FURNACE && type != InventoryType.FURNACE)
            return;
        if (!(event.getClickedInventory() instanceof FurnaceInventory))
            return;
        if (!(event.getWhoClicked() instanceof Player))
            return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
            return;

        Player player = (Player) event.getWhoClicked();

        if (event.getSlotType() != InventoryType.SlotType.RESULT)
            return;
        tracker.getPlayer(player).modEnergy(this, -CostType.COOKING.getCost() * event.getCurrentItem().getAmount());
    }
}
