package xyz.msws.energy.representation;

import xyz.msws.energy.representation.messengers.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum Medium {
    XP(XPMessenger.class), XP_LEVEL(LevelledXPMessenger.class), BOSSBAR(BossBarMessenger.class), BOSSBAR_LEVEL(LevelledBossBarMessenger.class), ACTIONBAR(ActionBarMessenger.class),
    CHAT(ChatMessenger.class), SCOREBOARD(ScoreboardMessenger.class), NONE(null);

    private Messenger rep;

    Medium(Class<? extends Messenger> c) {
        if (c == null) {
            rep = null;
            return;
        }
        try {
            Constructor<? extends Messenger> constructor = c.getConstructor();
            constructor.setAccessible(true);
            rep = constructor.newInstance();
            constructor.setAccessible(false);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Messenger getMessenger() {
        return rep;
    }
}
