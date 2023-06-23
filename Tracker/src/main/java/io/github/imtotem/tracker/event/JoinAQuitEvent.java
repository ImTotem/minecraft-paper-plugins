package io.github.imtotem.tracker.event;

import io.github.imtotem.tracker.Tracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinAQuitEvent implements Listener {

    private final Tracker instance = Tracker.getPlugin(Tracker.class);
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        if ( Tracker.target.getTarget().get(name) == null ) {
            Tracker.target.injectTarget(name, name);
        }
        if ( instance.toggleManager.getConfig().get(name) == null ) {
            instance.toggleManager.getConfig().set(name, false);
            instance.toggleManager.saveConfig();
        }
    }
}
