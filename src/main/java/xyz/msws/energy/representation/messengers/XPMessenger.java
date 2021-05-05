package xyz.msws.energy.representation.messengers;

import org.bukkit.entity.Player;

public class XPMessenger implements Messenger {
    @Override
    public void setEnergy(Player player, double level, double max) {
        float prog = (float) (level / max);
        player.sendExperienceChange(prog);
    }
}
