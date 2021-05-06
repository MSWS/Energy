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
import xyz.msws.energy.penalties.costs.*;
import xyz.msws.energy.penalties.penalties.ArcheryPenalty;

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
    }

    @Override
    protected void addModifiers() {
        addModifier(new MoveCost(plugin));
        addModifier(new CraftCost(plugin));
        addModifier(new BlockCost(plugin));
        addModifier(new SmeltCost(plugin));
        addModifier(new CombatCost(plugin));
        addModifier(new RegenCost(plugin));

        addModifier(new FoodCharger(plugin));
        addModifier(new SleepCharger(plugin));
    }

    @Override
    protected void addPenalties() {
        addPenalty(new ArcheryPenalty(plugin));
    }

    @Override
    public EnergyPlayer getPlayer(UUID uuid) {
        Entity ent = Bukkit.getEntity(uuid);
        if (ent == null)
            return null;

        if (players.containsKey(uuid))
            return players.get(uuid);

        EnergyPlayer player = new CorePlayer(plugin, uuid);
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
