package xyz.msws.energy.trackers;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.chargers.FoodCharger;
import xyz.msws.energy.chargers.SleepCharger;
import xyz.msws.energy.data.CorePlayer;
import xyz.msws.energy.data.EnergyPlayer;
import xyz.msws.energy.penalties.costs.CraftCost;
import xyz.msws.energy.penalties.costs.MoveCost;
import xyz.msws.energy.utils.MSG;

import java.util.UUID;

public class CoreTracker extends EnergyTracker {

    private NamespacedKey key;

    public CoreTracker(EnergyPlugin plugin) {
        super(plugin);
        key = new NamespacedKey(plugin, "energy");
    }

    @Override
    public void unload() {
        super.unload();
        for (Player p : Bukkit.getOnlinePlayers())
            unload(p);
    }

    @Override
    public void unload(Player p) {
        PersistentDataContainer container = p.getPersistentDataContainer();
        double e = getPlayer(p).getEnergy();
        container.set(key, PersistentDataType.DOUBLE, e);
        MSG.tell(p, "Set your PDC to %.2f", e);
    }

    @Override
    protected void addModifiers() {
        addModifier(new MoveCost(plugin));
        addModifier(new CraftCost(plugin));
        addModifier(new FoodCharger(plugin));
        addModifier(new SleepCharger(plugin));
    }

    @Override
    public EnergyPlayer getPlayer(UUID uuid) {
        Entity ent = Bukkit.getEntity(uuid);
        if (ent == null)
            return null;

        if (players.containsKey(uuid))
            return players.get(uuid);

        EnergyPlayer player = new CorePlayer(plugin.getEConfig(), uuid);
        PersistentDataContainer container = ent.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.DOUBLE)) {
            container.set(key, PersistentDataType.DOUBLE, plugin.getEConfig().getDefault());
        }

        player.setEnergy(container.get(key, PersistentDataType.DOUBLE));
        players.put(uuid, player);
        return player;
    }

    public NamespacedKey getKey() {
        return key;
    }
}