package xyz.msws.energy.representation.messengers;

import org.bukkit.entity.Player;

public class LevelledXPMessenger implements Messenger {
    @Override
    public void setEnergy(Player player, double level, double max) {
        int l = (int) Math.floor(level);
        float prog = (float) ((level % 1) / max) * 100;
        player.sendExperienceChange(prog, l);
    }
}
