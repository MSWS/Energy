package xyz.msws.energy.representation.messengers;

import org.bukkit.entity.Player;

public interface Messenger {
    void setEnergy(Player player, double level, double max);
}
