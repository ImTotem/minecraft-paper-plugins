package io.github.imtotem.corpse.listener;

import com.google.common.collect.Lists;
import io.github.imtotem.corpse.corpse.CorpseEntity;
import io.github.imtotem.corpse.gui.CorpseGUI;
import io.github.imtotem.corpse.trait.ExtraDropTrait;
import io.github.imtotem.corpse.util.HitBoxPart;
import net.citizensnpcs.api.gui.InventoryMenu;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.Objects;

public class CorpseRightClickListener implements Listener {

    @EventHandler
    public void click(PlayerInteractEntityEvent event) {
        if ( event.getRightClicked().getType() == EntityType.INTERACTION ) {
            Player player = event.getPlayer();
            GameMode gameMode = player.getGameMode();
            if ( gameMode == GameMode.SPECTATOR ) return;

            Interaction interaction = (Interaction) event.getRightClicked();
            String part = null;
            for ( HitBoxPart tmp : HitBoxPart.values() ) {
                if ( interaction.hasMetadata(tmp.name()) ) {
                    part = tmp.name();
                    break;
                }
            }

            if ( part == null ) return;

            NPC corpse = null;
            List<MetadataValue> metadataValueList = interaction.getMetadata(part);
            for ( MetadataValue metadataValue : metadataValueList ) {
                if (  metadataValue.value() instanceof NPC npc ) {
                    corpse = npc;
                    break;
                }
            }

            if ( corpse == null ) return;

            if ( player.getUniqueId().equals(corpse.getOrAddTrait(Owner.class).getOwnerId()) ) {
                giveItem(player, corpse);
                CorpseEntity.remove(corpse);
            } else {
                InventoryMenu.createSelfRegistered(new CorpseGUI(corpse)).present(player);
            }
        }
    }

    private void giveItem(Player player, NPC corpse) {
        List<ItemStack> drops = Lists.newArrayList(player.getInventory().getContents().clone());
        if ( corpse.hasTrait(ExtraDropTrait.class) )
            drops.addAll(corpse.getTraitNullable(ExtraDropTrait.class).getContents());
        player.getInventory().clear();

        PlayerInventory pInventory = player.getInventory();
        Inventory inventory = corpse.getOrAddTrait(Inventory.class);
        Equipment equipment = corpse.getOrAddTrait(Equipment.class);

        pInventory.setContents(inventory.getContents());

        pInventory.setItemInOffHand(equipment.get(Equipment.EquipmentSlot.OFF_HAND));

        pInventory.setHelmet(equipment.get(Equipment.EquipmentSlot.HELMET));
        pInventory.setChestplate(equipment.get(Equipment.EquipmentSlot.CHESTPLATE));
        pInventory.setLeggings(equipment.get(Equipment.EquipmentSlot.LEGGINGS));
        pInventory.setBoots(equipment.get(Equipment.EquipmentSlot.BOOTS));

        for ( ItemStack itemStack : drops.stream().filter(Objects::nonNull).toList() ) {
            if ( pInventory.firstEmpty() != -1 ) {
                pInventory.addItem(itemStack);
            } else {
                player.getWorld().dropItem(player.getLocation(), itemStack);
            }
        }
    }
}
