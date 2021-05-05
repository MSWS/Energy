package xyz.msws.energy.chargers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyModifier;
import xyz.msws.energy.utils.MSG;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SleepCharger extends EnergyModifier {
    public SleepCharger(EnergyPlugin plugin) {
        super(plugin);
    }

    private Map<UUID, Long> beds = new HashMap<>();

    @Override
    public String getDescription() {
        return "Sleeping";
    }

    @Override
    public void unregister() {
        PlayerBedEnterEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        beds.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onWake(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        long total = System.currentTimeMillis() - beds.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
        if (total < 5000)
            return;

        double e = tracker.getPlayer(player).getEnergy(), result = getResult(player);

        MSG.tell(player, "Original: %.2f, Result: %.2f (%.2f)", e, result, result - e);
        tracker.getPlayer(player).modEnergy(this, getResult(player));
    }

    private double getResult(Player p) {
        double e = tracker.getPlayer(p).getEnergy();
        return e + e * (p.getFoodLevel() / 30.0 + p.getHealth() / 50);
    }

}
