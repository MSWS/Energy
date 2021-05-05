package xyz.msws.energy.data;

import org.bukkit.entity.Entity;
import xyz.msws.energy.representation.Medium;
import xyz.msws.energy.representation.messengers.Messenger;
import xyz.msws.energy.trackers.EnergyModifier;

import java.util.UUID;

public class CorePlayer extends EnergyPlayer {
    public CorePlayer(EnergyConfig config, UUID uuid) {
        super(config, uuid);
    }

    public CorePlayer(EnergyConfig config, Entity player) {
        this(config, player.getUniqueId());
    }

    @Override
    public void modEnergy(EnergyModifier cost, double amo) {
        this.energy += amo;
        this.energy = Math.max(Math.min(this.energy, config.getMax()), 0);
        updateMessenger();
    }

    @Override
    public Messenger getMessenger() {
        return Medium.XP_LEVEL.getMessenger();
    }
}
