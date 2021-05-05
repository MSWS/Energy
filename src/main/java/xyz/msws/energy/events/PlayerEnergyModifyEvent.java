package xyz.msws.energy.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import xyz.msws.energy.data.EnergyPlayer;
import xyz.msws.energy.trackers.EnergyModifier;

public class PlayerEnergyModifyEvent extends PlayerEnergyEvent implements Cancellable {

    private static final HandlerList handler = new HandlerList();

    private double change;
    private boolean cancel = false;
    private final EnergyModifier modifier;

    public PlayerEnergyModifyEvent(EnergyPlayer player, EnergyModifier modifier, double change) {
        super(player);
        this.change = change;
        this.modifier = modifier;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public EnergyModifier getModifier() {
        return this.modifier;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
