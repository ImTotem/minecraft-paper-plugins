package io.github.imtotem.corpse.event;

import io.github.imtotem.corpse.corpse.Corpse;
import io.github.imtotem.corpse.corpse.CorpseList;
import io.github.imtotem.corpse.corpse.PacketReader;
import io.github.imtotem.corpse.corpse.RightClickCorpse;
import net.minecraft.world.entity.Pose;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CorpseInteract implements Listener {

    @EventHandler
    public void onInteract(RightClickCorpse event) {
        Player player = event.getPlayer();
        Corpse corpse = event.getCorpse();
        GameMode gameMode = player.getGameMode();
//        if ( corpse.getPose() != Pose.SLEEPING ) {
//            return;
//        }
        if ( gameMode == GameMode.SURVIVAL || gameMode == GameMode.CREATIVE || gameMode == GameMode.ADVENTURE ) {
            if ( corpse.whoseCorpse().toString().equals(player.getUniqueId().toString()) ) {
                Inventory inventory = corpse.getCorpseInventory();
                ArrayList<ItemStack> itemStacks = new ArrayList<>();
                for ( int i = 0; i < 41; i++ ) {
                    if ( inventory.getItem(i) != null ) {
                        if ( player.getInventory().getItem(i) == null )
                            player.getInventory().setItem(i, inventory.getItem(i));
                        else itemStacks.add(inventory.getItem(i));
                    }
                }
                for ( int i = 41; i < 45; i++ )
                    if ( inventory.getItem(i) != null )
                        itemStacks.add(inventory.getItem(i));
                if ( !itemStacks.isEmpty() ) {
                    itemStacks.forEach(player.getInventory()::addItem);
                }
                corpse.remove();
            }
            else {
                player.openInventory(corpse.getCorpseInventory());
            }
        }
    }

    @EventHandler
    public void whenCloseInventory(InventoryCloseEvent event) {
        InventoryHolder holder = event.getView().getTopInventory().getHolder();
        if ( CorpseList.contains(holder) ) {
            Corpse corpse = CorpseList.get(holder);
            if ( corpse.getInventory().isEmpty() ) {
                for ( HumanEntity p : corpse.getInventory().getViewers() ) {
                    if ( p != event.getPlayer() )
                        p.closeInventory();
                }
                corpse.remove();
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new PacketReader(event.getPlayer()).inject();
        CorpseList.loadCorpseTo(event.getPlayer());
    }
}
