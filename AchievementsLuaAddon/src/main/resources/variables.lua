--[[
    Achievements Lua Addon
    
    Only modify this class if you know what you are doing
]]

Bukkit = luajava.bindClass("org.bukkit.Bukkit")
Server = Bukkit:getServer()
ChatColor = luajava.bindClass("org.bukkit.ChatColor")
Material = luajava.bindClass("org.bukkit.Material")
Action = luajava.bindClass("org.bukkit.event.block.Action")
-- AchievementApi = luajava.bindClass("codes.goblom.achievements.AchievementApi")

function toString(object) 
    return object:toString()
end

function getClassName(object)
    return object:getClass():getSimpleName()
end