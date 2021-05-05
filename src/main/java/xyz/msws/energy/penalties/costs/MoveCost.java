package xyz.msws.energy.penalties.costs;

import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyModifier;

public class MoveCost extends EnergyModifier {
    public MoveCost(EnergyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getDescription() {
        return "Movement";
    }

    @Override
    public void unregister() {
        PlayerMoveEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom(), to = event.getTo();
        if (from.getBlock().equals(to.getBlock()))
            return;
        double e = player.isSneaking() ? CostType.CROUCH_WALK.getCost() : CostType.WALKING.getCost();
        if (player.isSprinting())
            e = CostType.SPRINTING.getCost();
        if (from.getBlockY() < to.getBlockY()) {
            e = CostType.JUMPING.getCost();
            if (player.isSneaking())
                e = CostType.CROUCH_JUMP.getCost();
            if (player.isSprinting())
                e = CostType.SPRINT_JUMP.getCost();
        }

        if (player.isGliding())
            e = CostType.ELYTRA.getCost();

        if (player.isInsideVehicle()) {
            if (player.getVehicle() instanceof Boat)
                e = CostType.ROWING.getCost();
            if (player.getVehicle() instanceof Horse)
                e = CostType.RIDING.getCost();
        }

        if (player.getFallDistance() > 2)
            e = CostType.FALLING.getCost();

        tracker.getPlayer(player).modEnergy(this, -e);
    }
}
