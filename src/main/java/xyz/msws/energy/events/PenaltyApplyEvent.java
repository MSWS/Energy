package xyz.msws.energy.events;

import org.bukkit.event.HandlerList;
import xyz.msws.energy.data.EnergyPlayer;
import xyz.msws.energy.penalties.penalties.EnergyPenalty;

public class PenaltyApplyEvent extends PlayerEnergyEvent {
    private static final HandlerList handler = new HandlerList();
    private final EnergyPenalty penalty;

    public PenaltyApplyEvent(EnergyPlayer player, EnergyPenalty penalty) {
        super(player);
        this.penalty = penalty;
    }

    public EnergyPenalty getPenalty() {
        return this.penalty;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }
}
