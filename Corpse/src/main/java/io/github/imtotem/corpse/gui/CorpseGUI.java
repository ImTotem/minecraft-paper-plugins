package io.github.imtotem.corpse.gui;

import io.github.imtotem.corpse.trait.ExtraDropTrait;
import net.citizensnpcs.api.gui.InventoryMenuPage;
import net.citizensnpcs.api.gui.InventoryMenuSlot;
import net.citizensnpcs.api.gui.Menu;
import net.citizensnpcs.api.gui.MenuContext;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

@Menu(type = InventoryType.CHEST, dimensions = { 8, 9 })
public class CorpseGUI extends InventoryMenuPage {
    private final NPC corpse;

    public CorpseGUI(NPC corpse) {
        this.corpse = corpse;
    }

    @Override
    public void initialise(MenuContext context) {

        context.getInventory().addItem(filtering(corpse.getOrAddTrait(Inventory.class).getContents()));
        corpse.getOrAddTrait(Equipment.class).getEquipmentBySlot().forEach((equipmentSlot, itemStack) -> {
            if ( equipmentSlot != Equipment.EquipmentSlot.HAND && itemStack != null )
                context.getInventory().addItem(itemStack);
        });
        context.getInventory().addItem(filtering(corpse.getOrAddTrait(ExtraDropTrait.class).getContentsToArray()));
    }

    @Override
    public void onClick(InventoryMenuSlot slot, InventoryClickEvent event) {
        event.setCancelled(true);
    }

    private ItemStack[] filtering(ItemStack[] contents) {
        return Arrays.stream(contents).filter(Objects::nonNull).toArray(ItemStack[]::new);
    }
}
