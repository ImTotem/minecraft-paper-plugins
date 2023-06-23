package io.github.imtotem.tracker.event;

import io.github.imtotem.tracker.Tracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        String name = event.getEntity().getName();
        Tracker.deadLocation.put(name, event.getEntity().getLocation());
        Tracker.target.injectTarget(name, "dLoc");
    }
}
