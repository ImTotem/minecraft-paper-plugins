package io.github.imtotem.corpse.listener;

import io.github.imtotem.corpse.corpse.CorpseEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;


public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        if ( !player.getInventory().isEmpty() && player.getLastDamageCause() != null ) {
            DamageCause cause = player.getLastDamageCause().getCause();
            if (cause == DamageCause.LAVA || cause == DamageCause.VOID || cause == DamageCause.WORLD_BORDER) {
                if ( player.getBedSpawnLocation() == null )
                    CorpseEntity.spawn(player, player.getWorld().getSpawnLocation());

                else CorpseEntity.spawn(player, player.getBedSpawnLocation());
            } else {
                CorpseEntity.spawn(player);
            }
        }

        event.getDrops().clear();
    }
}
