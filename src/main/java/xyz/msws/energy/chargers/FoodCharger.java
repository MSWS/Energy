package xyz.msws.energy.chargers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.trackers.EnergyModifier;
import xyz.msws.energy.utils.MSG;

import java.util.EnumMap;
import java.util.Map;

public class FoodCharger extends EnergyModifier {
    public FoodCharger(EnergyPlugin plugin) {
        super(plugin);
    }

    private static final Map<Material, Integer> points = new EnumMap<>(Material.class);

    static {
        points.put(Material.APPLE, 4);
        points.put(Material.BAKED_POTATO, 5);
        points.put(Material.BEETROOT, 1);
        points.put(Material.BEETROOT_SOUP, 8);
        points.put(Material.BREAD, 5);
        points.put(Material.CARROT, 3);
        points.put(Material.CHORUS_FRUIT, 4);
        points.put(Material.COOKED_CHICKEN, 6);
        points.put(Material.COOKED_COD, 5);
        points.put(Material.COOKED_MUTTON, 6);
        points.put(Material.COOKED_RABBIT, 5);
        points.put(Material.COOKED_SALMON, 6);
        points.put(Material.COOKIE, 2);
        points.put(Material.DRIED_KELP, 1);
        points.put(Material.ENCHANTED_GOLDEN_APPLE, 4);
        points.put(Material.GOLDEN_APPLE, 4);
        points.put(Material.GOLDEN_CARROT, 6);
        points.put(Material.HONEY_BOTTLE, 6);
        points.put(Material.MELON_SLICE, 2);
        points.put(Material.MUSHROOM_STEW, 6);
        points.put(Material.POISONOUS_POTATO, 2);
        points.put(Material.POTATO, 1);
        points.put(Material.PUFFERFISH, 1);
        points.put(Material.PUMPKIN_PIE, 8);
        points.put(Material.RABBIT_STEW, 10);
        points.put(Material.BEEF, 3);
        points.put(Material.CHICKEN, 2);
        points.put(Material.COD, 2);
        points.put(Material.MUTTON, 2);
        points.put(Material.PORKCHOP, 3);
        points.put(Material.RABBIT, 3);
        points.put(Material.SALMON, 2);
        points.put(Material.ROTTEN_FLESH, 4);
        points.put(Material.SPIDER_EYE, 2);
        points.put(Material.COOKED_BEEF, 8);
        points.put(Material.SUSPICIOUS_STEW, 6);
        points.put(Material.SWEET_BERRIES, 2);
        points.put(Material.TROPICAL_FISH, 1);
    }

    @Override
    public String getDescription() {
        return "Food";
    }

    @Override
    public void unregister() {
        PlayerItemConsumeEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        MSG.tell(player, "FP: %d, EP: %.2f, Total: %.2f", points.get(event.getItem().getType()), tracker.getPlayer(player).getEnergy(), eval(player, event.getItem().getType()));

        tracker.getPlayer(player).modEnergy(this, eval(player, event.getItem().getType()));
    }

    private double eval(Player player, Material food) {
        double fp = points.getOrDefault(food, 0);
        double x = tracker.getPlayer(player).getEnergy();
        return fp / (1 + x / 100);
    }

}
