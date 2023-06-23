package com.imtotem.chatsound.event;

import com.imtotem.chatsound.Chatsound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public class JoinEvent implements Listener {
    private final Plugin instance = Chatsound.getPlugin(Chatsound.class);

    @EventHandler
    public void onPlayerFirstJoinEvent(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        String uuid = p.getUniqueId().toString();

        if ( !instance.getConfig().getBoolean("uuid." + uuid) ) {
            instance.getConfig().set("uuid." + uuid, false);
        }
        instance.saveConfig();
    }
}
