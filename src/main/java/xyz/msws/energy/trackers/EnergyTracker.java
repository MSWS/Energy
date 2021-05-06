package xyz.msws.energy.trackers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.data.EnergyPlayer;
import xyz.msws.energy.events.PlayerEnergyModifyEvent;
import xyz.msws.energy.events.PlayerEnergySetEvent;
import xyz.msws.energy.penalties.penalties.EnergyPenalty;
import xyz.msws.energy.utils.MSG;

import java.util.*;

public abstract class EnergyTracker implements Listener {
    protected List<EnergyModifier> modifiers = new ArrayList<>();
    protected Map<UUID, EnergyPlayer> players = new HashMap<>();
    protected List<EnergyPenalty> penalties = new ArrayList<>();
    protected Map<UUID, List<EnergyPenalty>> active = new HashMap<>();

    protected EnergyPlugin plugin;

    public EnergyTracker(EnergyPlugin plugin) {
        this.plugin = plugin;
    }

    public List<EnergyModifier> getModifiers() {
        return modifiers;
    }

    public void addModifier(EnergyModifier mod) {
        modifiers.add(mod);
    }

    public void addPenalty(EnergyPenalty penalty) {
        penalties.add(penalty);
    }

    public void load() {
        addModifiers();
        addPenalties();
        loadModifiers();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        unload(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChange(PlayerEnergyModifyEvent event) {
        Player player = Bukkit.getPlayer(event.getPlayer().getPlayer());
        if (player == null)
            return;
        double energy = event.getPlayer().getEnergy();
        double old = energy / plugin.getEConfig().getMax(), now = (energy + event.getChange()) / plugin.getEConfig().getMax();
        updatePenalties(player, old, now);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSet(PlayerEnergySetEvent event) {
        Player player = Bukkit.getPlayer(event.getPlayer().getPlayer());
        if (player == null)
            return;
        double energy = event.getPlayer().getEnergy();
        double old = energy / plugin.getEConfig().getMax(), now = (event.getChange()) / plugin.getEConfig().getMax();
        updatePenalties(player, old, now);
    }

    private void updatePenalties(Player player, double old, double now) {
        List<EnergyPenalty> ap = active.getOrDefault(player.getUniqueId(), new ArrayList<>());
        for (EnergyPenalty ep : penalties) {
            if (old > ep.getMax() || old < ep.getMin()) {
                if (!ap.contains(ep))
                    continue;
                ep.remove(player);
                ap.remove(ep);
                MSG.tell(player, "You no longer have: %s", ep.getDescription(old));
                continue;
            }
            if (ap.contains(ep)) {
                if (!ep.update(player, old, now))
                    continue;
                MSG.tell(player, "You now have: %s", ep.getDescription(now));
                continue;
            }
            MSG.tell(player, "You now have: %s", ep.getDescription(now));
            ap.add(ep);
            ep.apply(player.getUniqueId(), now);
        }
        active.put(player.getUniqueId(), ap);
    }

    public void unload() {
        PlayerQuitEvent.getHandlerList().unregister(this);
    }

    public abstract void unload(Player p);

    protected abstract void addModifiers();

    protected abstract void addPenalties();

    protected void loadModifiers() {
        getModifiers().forEach(EnergyModifier::register);
    }

    public Map<UUID, EnergyPlayer> getPlayers() {
        return players;
    }

    public abstract EnergyPlayer getPlayer(UUID uuid);

    public EnergyPlayer getPlayer(Entity ent) {
        return getPlayer(ent.getUniqueId());
    }

}
