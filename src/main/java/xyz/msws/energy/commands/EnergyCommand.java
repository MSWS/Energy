package xyz.msws.energy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.msws.energy.EnergyPlugin;
import xyz.msws.energy.utils.MSG;

import java.util.HashMap;
import java.util.Map;

public class EnergyCommand implements CommandExecutor {

    private Map<String, Command> cmds = new HashMap<>();

    private EnergyPlugin plugin;

    public EnergyCommand(EnergyPlugin plugin) {
        this.plugin = plugin;

        cmds.put("set", new SetCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            MSG.tell(sender, "Please specify a sub-command.");
            return true;
        }

        if (!cmds.containsKey(args[0].toLowerCase()))
            return true;

        cmds.get(args[0].toLowerCase()).execute(sender, label, args);
        return true;
    }
}
