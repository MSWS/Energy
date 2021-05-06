package xyz.msws.energy.data;

import org.bukkit.configuration.ConfigurationSection;

public abstract class Capsule<T> {
    protected ConfigurationSection data;
    protected T obj;

    public Capsule(ConfigurationSection data) {
        this.data = data;
    }

    public T load(boolean force) {
        if (force || obj == null)
            obj = load();
        return obj;
    }

    protected abstract T load();

}
