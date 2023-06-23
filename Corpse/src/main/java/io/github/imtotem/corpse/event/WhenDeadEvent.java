package io.github.imtotem.corpse.event;

import io.github.imtotem.corpse.corpse.Corpse;
import io.github.imtotem.corpse.corpse.CorpseList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;

public class WhenDeadEvent implements Listener {

    @EventHandler
    public void whenDead(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        player.getInventory().addItem(player.getItemOnCursor());
        player.setItemOnCursor(null);

        if ( !(player.getInventory().isEmpty() && player.getOpenInventory().getTopInventory().isEmpty()) ) {
            Corpse corpse = Corpse.create(player);
            CorpseList.add(corpse);
            corpse.showAll();

            if ( player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING )
                for ( int i = 0; i < 5; i++ )
                    if ( player.getOpenInventory().getTopInventory().getContents()[i] != null )
                        player.getOpenInventory().getTopInventory().clear();

        }

        event.getDrops().clear();
    }
}
