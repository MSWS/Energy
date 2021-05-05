package xyz.msws.energy.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.msws.energy.penalties.penalties.EnergyPenalty;
import xyz.msws.energy.representation.messengers.Messenger;
import xyz.msws.energy.trackers.EnergyModifier;

import java.util.Map;
import java.util.UUID;

public abstract class EnergyPlayer {
    protected EnergyConfig config;
    protected UUID uuid;
    protected Map<EnergyPenalty, Long> penalties;
    protected double energy;

    public EnergyPlayer(EnergyConfig config, UUID uuid) {
        this.config = config;
        this.uuid = uuid;
    }

    public UUID getPlayer() {
        return uuid;
    }

    public Map<EnergyPenalty, Long> getPenalties() {
        return penalties;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double e) {
        this.energy = e;
    }

    public abstract void modEnergy(EnergyModifier cost, double amo);

    public void updateMessenger() {
        Player ent = Bukkit.getPlayer(uuid);
        if (ent == null)
            return;
        getMessenger().setEnergy(ent, this.energy, this.config.getMax());
    }

    public abstract Messenger getMessenger();

    public void addPenalty(EnergyPenalty penalty) {
        addPenalty(penalty, -1);
    }

    public void addPenalty(EnergyPenalty penalty, long time) {
        penalty.apply(uuid);
        penalties.put(penalty, time);
    }

    public boolean hasPenalty(EnergyPenalty penalty) {
        long v = penalties.getOrDefault(penalty, 0L);
        if (v == -1)
            return true;
        return System.currentTimeMillis() < v;
    }

}
