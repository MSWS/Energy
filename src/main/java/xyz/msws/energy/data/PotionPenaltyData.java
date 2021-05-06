package xyz.msws.energy.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PotionPenaltyData extends Capsule<PotionPenaltyData> {
    public PotionPenaltyData(ConfigurationSection data, String name) {
        super(data);
        this.name = name;
    }

    private String name;
    private PotionEffectType type;
    private List<Double> ranges = new ArrayList<>();
    private List<Integer> durations = new ArrayList<>();
    private List<Integer> amplifiers = new ArrayList<>();
    private double min, max;

    private PotionEffect effect;

    @Override
    public PotionPenaltyData load() {
        type = PotionEffectType.getByName(name);
        if (type == null)
            throw new IllegalArgumentException("Unknown potion type: " + name);
        if (data.contains("Ranges")) {
            ranges = data.getDoubleList("Ranges");
            max = ranges.get(0);
            min = ranges.get(ranges.size() - 1);
        } else if (data.contains("Max") || data.contains("Min")) {
            min = data.getDouble("Min", 0.0);
            max = data.getDouble("Max", 100.0);
            ranges.add(max);
            ranges.add(min);
        } else {
            throw new IllegalArgumentException("No range or min/max specified for " + data.getName());
        }

        if (data.contains("Amplifiers")) {
            amplifiers = data.getIntegerList("Amplifiers");
        } else {
            amplifiers = Arrays.asList(data.getInt("Amplifier", 1));
        }

        if (data.contains("Durations")) {
            durations = data.getIntegerList("Durations");

        } else {
            durations = Collections.singletonList(data.getInt("Duration", -1));
            durations = Arrays.asList(data.getInt("Duration", -1));

        }
        for (int i = 0; i < durations.size(); i++)
            durations.set(i, durations.get(i) == -1 ? Integer.MAX_VALUE : durations.get(i) * 20);

        return this;
    }

    public void apply(LivingEntity entity, double prog) {
        entity.addPotionEffect(getEffect(prog));
    }

    public PotionEffect getEffect(double prog) {
        prog *= 100;
        int index = -1;
        for (int i = 0; i < ranges.size(); i++) {
            double d = ranges.get(i);
            if (prog <= d)
                index = i;
        }

        if (index == -1)
            return new PotionEffect(type, 0, 0);
        return new PotionEffect(type, durations.get(index % durations.size()), amplifiers.get(index % amplifiers.size()) - 1);
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public String getName() {
        return name;
    }

    public void remove(LivingEntity entity) {
        entity.removePotionEffect(type);
    }

}
