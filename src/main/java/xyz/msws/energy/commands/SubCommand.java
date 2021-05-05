package xyz.msws.energy.commands;

import org.bukkit.command.Command;
import xyz.msws.energy.EnergyPlugin;

public abstract class SubCommand extends Command {

    protected EnergyPlugin plugin;

    protected SubCommand(String name, EnergyPlugin plugin) {
        super(name);
        this.plugin = plugin;
    }
}
