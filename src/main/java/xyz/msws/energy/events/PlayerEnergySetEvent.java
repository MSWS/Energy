package xyz.msws.energy.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import xyz.msws.energy.data.EnergyPlayer;

public class PlayerEnergySetEvent extends PlayerEnergyEvent implements Cancellable {

    private static final HandlerList handler = new HandlerList();

    private double change;
    private boolean cancel = false;

    public PlayerEnergySetEvent(EnergyPlayer player, double change) {
        super(player);
        this.change = change;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
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
