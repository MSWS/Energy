package xyz.msws.energy.representation.messengers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import xyz.msws.energy.utils.MSG;

public class ActionBarMessenger implements Messenger {
    @Override
    public void setEnergy(Player player, double level, double max) {
        String message = String.format("Energy: %.2f/%d", level, (int) max);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MSG.color(message)));
    }
}
