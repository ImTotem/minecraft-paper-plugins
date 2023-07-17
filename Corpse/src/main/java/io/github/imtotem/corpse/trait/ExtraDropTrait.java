package io.github.imtotem.corpse.trait;

import com.google.common.collect.Lists;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@TraitName("extradrop")
public class ExtraDropTrait extends Trait {
    @Persist(value = "extradrop")
    private List<ItemStack> extradrop = Lists.newArrayList();

    public ExtraDropTrait() {
        super("extradrop");
    }

    public void setContents(List<ItemStack> contents) {
        this.extradrop = Lists.newArrayList(contents);
    }

    public List<ItemStack> getContents() {
        return Lists.newArrayList(this.extradrop);
    }

    public ItemStack[] getContentsToArray() {
        return Lists.newArrayList(this.extradrop).toArray(ItemStack[]::new);
    }
}
