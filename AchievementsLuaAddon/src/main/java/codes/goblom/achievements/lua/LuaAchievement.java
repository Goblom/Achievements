/*
 * Copyright 2015 Goblom.
 */
package codes.goblom.achievements.lua;

import codes.goblom.achievements.api.Achievement;
import codes.goblom.achievements.api.AchievementData;
import org.bukkit.event.Event;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 *
 * @author Goblom
 */
public class LuaAchievement extends Achievement {

    private final LuaFunction compatible, test;

    public LuaAchievement(String name, String description, LuaFunction compatible, LuaFunction test) {
        this(name, description, AchievementData.Type.GLOBAL, compatible, test);
    }

    public LuaAchievement(String name, String description, AchievementData.Type type, LuaFunction compatible, LuaFunction requirements) {
        super(name, description, type);

        this.compatible = compatible;
        this.test = requirements;
    }

    @Override
    public boolean isCompatibleEvent(Event event) {
        return compatible.call(CoerceJavaToLua.coerce(event.getClass().getSimpleName())).checkboolean();
    }

    @Override
    public boolean test(Event t) {
        LuaValue value = test.call(CoerceJavaToLua.coerce(t));
        
        if (value.isnil()) return false;
        if (value.isint()) {
            return value.checkint() == 1;
        } else if (value.isboolean()) {
            return value.checkboolean();
        }
        
        return false;
    }
}
