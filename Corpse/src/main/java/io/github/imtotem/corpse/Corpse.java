package io.github.imtotem.corpse;

import io.github.imtotem.corpse.command.CorpseCommand;
import io.github.imtotem.corpse.listener.CorpseRightClickListener;
import io.github.imtotem.corpse.listener.PlayerDeathListener;
import io.github.imtotem.corpse.trait.ExtraDropTrait;
import io.github.imtotem.corpse.trait.HitBoxTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class Corpse extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfig();

        Plugin depend = getServer().getPluginManager().getPlugin("Citizens");
        if ( depend == null || !depend.isEnabled() ) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(ExtraDropTrait.class));
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(HitBoxTrait.class));

        initCommands();
        initEvents();
    }

    @Override
    public void onDisable() {
        CitizensAPI.getNPCRegistry().forEach(npc -> {
            npc.getOrAddTrait(HitBoxTrait.class).removeAll();
        });
    }

    public void initEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new CorpseRightClickListener(), this);
    }

    public void initCommands() {
        Objects.requireNonNull(getCommand("corpse")).setExecutor(new CorpseCommand());
        Objects.requireNonNull(getCommand("corpse")).setTabCompleter(new CorpseCommand());
    }

    public void loadConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
