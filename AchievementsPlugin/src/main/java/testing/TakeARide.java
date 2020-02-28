/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package testing;

import codes.goblom.achievements.api.Achievement;
import org.bukkit.event.Event;
import org.bukkit.event.vehicle.VehicleEnterEvent;

/**
 *
 * @author Goblom
 */
public class TakeARide extends Achievement {

    public TakeARide() {
        super("Lets take a ride.", "Enter a vehicle", Type.SERVER);
    }

    @Override
    public boolean isCompatibleEvent(Event event) {
        return event instanceof VehicleEnterEvent;
    }

    @Override
    public boolean test(Event t) {
        return true;
    }
    
}
