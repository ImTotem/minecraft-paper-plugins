package com.imtotem.glowing;

import com.imtotem.glowing.command.GlowingCommand;
import com.imtotem.glowing.time.TabManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class AlwaysGlowing extends JavaPlugin implements Listener {
    public static boolean isOk = true;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        for ( Player player : Bukkit.getServer().getOnlinePlayers() ) {
            addPotionEffect(player);
        }

        try {
            Logger coreLogger = (Logger) LogManager.getRootLogger();
            coreLogger.addFilter(new ColorList());
        } catch (Exception e) { isOk = false; }

        Objects.requireNonNull(getCommand("glow")).setExecutor(new GlowingCommand());
        Objects.requireNonNull(getCommand("glow")).setTabCompleter(new GlowingCommand());

        new TabManager(this).showTab();
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        addPotionEffect(event.getPlayer());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        addPotionEffect(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDrinkMilk(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MILK_BUCKET) {
            addPotionEffect(event.getPlayer());
        }
    }

    public void addPotionEffect(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 9999999, 255, true, false));
            }
        }.runTaskLater(this, 1);
    }
}
