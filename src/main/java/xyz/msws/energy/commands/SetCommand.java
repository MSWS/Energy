package xyz.msws.energy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.utils.MSG;

public class SetCommand extends SubCommand {
    protected SetCommand(EnergyPlugin plugin) {
        super("set", plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        Player target = null;
        // /energy set <player> [amo]
        if (sender instanceof Player)
            target = (Player) sender;
        if (args.length == 3 && sender.hasPermission("energy.command.set.others"))
            target = Bukkit.getPlayer(args[1]);

        double e = 0;
        try {
            e = Double.parseDouble(args[args.length - 1]);
        } catch (NumberFormatException unused) {
            MSG.tell(sender, "Unknown number");
            return true;
        }

        if (target == null) {
            MSG.tell(sender, "Unknown target");
            return true;
        }


        plugin.getTracker().getPlayer(target).setEnergy(e);
        return true;
    }
}
