package io.github.imtotem.corpse.corpse;

import com.google.common.collect.Lists;
import io.github.imtotem.corpse.trait.ExtraDropTrait;
import io.github.imtotem.corpse.trait.HitBoxTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.trait.CurrentLocation;
import net.citizensnpcs.trait.Gravity;
import net.citizensnpcs.trait.SleepTrait;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class CorpseEntity {
    public static void spawn(Player player) {
        spawn(player, player.getLocation());
    }

    public static void spawn(Player player, Location location) {
        Location newLoc = removeRotation(location);
        createCorpse(player, newLoc).spawn(newLoc);

        save();
    }

    private static NPC createCorpse(Player player, Location location) {
        NPC corpse = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());

        corpse.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, false);

        corpse.getOrAddTrait(Owner.class).setOwner(player.getUniqueId());

        corpse.getOrAddTrait(CurrentLocation.class).setLocation(location);
        corpse.getOrAddTrait(HitBoxTrait.class);

        corpse.getOrAddTrait(SleepTrait.class).setSleeping(location);
        corpse.getOrAddTrait(Gravity.class).gravitate(true);

        List<ItemStack> extraDrop = Lists.newArrayList();

        ItemStack onCursor = player.getItemOnCursor();
        if (onCursor.getType() != Material.AIR) {
            extraDrop.add(player.getItemOnCursor());
            player.setItemOnCursor(null);
        }

        org.bukkit.inventory.Inventory topInventory = player.getOpenInventory().getTopInventory();
        if ( topInventory.getType() == InventoryType.CRAFTING ) {
            for ( int i = 1; i < 5; i++ ) {
                ItemStack itemStack = topInventory.getItem(i);
                if ( itemStack != null && itemStack.getType() != Material.AIR )
                    extraDrop.add(topInventory.getItem(i));
            }
            topInventory.clear();
        }

        PlayerInventory inventory = player.getInventory();
        int idx = 0;
        while ( inventory.firstEmpty() != -1 && !extraDrop.isEmpty()) {
            inventory.addItem(extraDrop.get(idx));
            extraDrop.remove(idx++);
        }

        corpse.getOrAddTrait(Inventory.class).setContents(inventory.getStorageContents());

        corpse.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.OFF_HAND, inventory.getItemInOffHand());

        corpse.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, inventory.getHelmet());
        corpse.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, inventory.getChestplate());
        corpse.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, inventory.getLeggings());
        corpse.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, inventory.getBoots());

        if ( !extraDrop.isEmpty() ) {
            corpse.getOrAddTrait(ExtraDropTrait.class).setContents(extraDrop);
        }

        return corpse;
    }

    public static void remove(NPC corpse) {
        CitizensAPI.getNPCRegistry().deregister(corpse);

        save();
    }

    private static Location removeRotation(Location location) {
        Location newLoc = location.clone();
        newLoc.setPitch(0f);
        newLoc.setYaw(0f);

        return newLoc;
    }

    private static void save() {
        CitizensAPI.getNPCRegistry().saveToStore();
    }
}
