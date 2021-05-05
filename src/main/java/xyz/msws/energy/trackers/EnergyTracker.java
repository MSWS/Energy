package xyz.msws.energy.trackers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.data.EnergyPlayer;

import java.util.*;

public abstract class EnergyTracker implements Listener {
    protected List<EnergyModifier> modifiers = new ArrayList<>();
    protected Map<UUID, EnergyPlayer> players = new HashMap<>();

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

    public void load() {
        addModifiers();
        loadModifiers();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        unload(event.getPlayer());
    }

    public void unload() {
        PlayerQuitEvent.getHandlerList().unregister(this);
    }

    public abstract void unload(Player p);

    protected abstract void addModifiers();

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
