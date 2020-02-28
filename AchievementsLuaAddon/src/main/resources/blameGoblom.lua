-------------- Required Achievement Functions -------------- 
-- setName(String)
-- setIsCompatible(function(eventName))     >> This checks if "eventName" is the correct event for this achievement
-- setTestFunction(function(event))         >> if isCompatible returns true then this is ran to determin if the player 
--      >> setTest(function(event))             >> associated should be awarded the achievement
--      >> setTesting(function(event))

-------------- Optional Achievement Functions -------------- 
-- setDescription(String)
-- setType(String)                          >> Can ONLY be "Global" or "Server"

-------------- Achievement Modification Functions --------------
-- register()                               >> Once you have finished with the achievement info run this
--      >> finish()                             >> it will register the achievement with the server
--      >> build()
-- get()                                    >> returns the current RegisteredAchievement once register() has been ran
--      >> getAchievement()                     >> this will error if achievement hasn't been registered
-- getAchievementFromName(String)           >> returns an achievement with the given name
-- getAchievementFromId(int)                >> returns an achievement with the given id


print("Creating BlameGoblom Achievement")

setName("Blame Goblom")
setDescription("He is the source of our problems")
setType("Global") -- "Global" or "Server"

setIsCompatible( 
    function(event)
        return event == "AsyncPlayerChatEvent" or event == "PlayerChatEvent"
    end
)

--[[
    setTest(function(event))
    setTesting(function(event))
    setTestFunction(function(event))
]]
setTestFunction( 
    function(event)
        return event:getMessage():contains("#BlameGoblom")
    end
)

--[[
    finish()
    register()
    build()
]]
register()

print("Finished BlameGoblom Achievement")