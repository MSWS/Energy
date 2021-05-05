package xyz.msws.energy.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.msws.energy.data.EnergyPlayer;

public class PlayerEnergyEvent extends Event {
    private static final HandlerList handler = new HandlerList();
    protected EnergyPlayer player;

    public PlayerEnergyEvent(EnergyPlayer player) {
        this.player = player;
    }

    public EnergyPlayer getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }
}
