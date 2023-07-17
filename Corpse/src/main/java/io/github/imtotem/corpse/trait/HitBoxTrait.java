package io.github.imtotem.corpse.trait;

import io.github.imtotem.corpse.Corpse;
import io.github.imtotem.corpse.util.HitBoxPart;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.trait.CurrentLocation;
import org.bukkit.Location;
import org.bukkit.entity.Interaction;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

@TraitName("hitbox")
public class HitBoxTrait extends Trait {
    private final double[] SUB = {0.3f, 0.6f, 0.6f};

    Interaction[] interactions = new Interaction[SUB.length];

    public HitBoxTrait() {
        super("hitbox");
    }

    @Override
    public void onSpawn() {
        Location newLoc = this.getNPC().getOrAddTrait(CurrentLocation.class).getLocation().clone();
        newLoc.setY(newLoc.getY()-0.3f);

        // -0.43, -1.33(-0.9)
        for (int i = 0; i < SUB.length; i++) {
            newLoc.setX(newLoc.getX()-SUB[i]);
            interactions[i] = spawnInteraction(newLoc);

            interactions[i].setMetadata(HitBoxPart.values()[i].name(), new FixedMetadataValue(JavaPlugin.getPlugin(Corpse.class), this.getNPC()) );
        }
    }

    @Override
    public void onDespawn() {
        removeAll();
    }

    @Override
    public void onRemove() {
        removeAll();
    }

    public void removeAll() {
        for ( Interaction interaction : interactions ) {
            if ( interaction != null )interaction.remove();
        }
    }

    private Interaction spawnInteraction(Location location) {
        return location.getWorld().spawn(location, Interaction.class, interaction -> {
            interaction.setInteractionWidth(0.6f);
            interaction.setInteractionHeight(0.6f);
        });
    }
}
