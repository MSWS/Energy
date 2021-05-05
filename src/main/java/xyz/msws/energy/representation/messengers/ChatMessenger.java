package xyz.msws.energy.representation.messengers;

import org.bukkit.entity.Player;
import xyz.msws.energy.utils.MSG;

public class ChatMessenger implements Messenger {
    @Override
    public void setEnergy(Player player, double level, double max) {
        MSG.tell(player, "Level: %.02f / %d", level, (int) max);
    }
}
