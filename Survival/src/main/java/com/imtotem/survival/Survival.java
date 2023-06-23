package com.imtotem.survival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static com.imtotem.survival.CommandInit.initCommands;

public final class Survival extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("[Survival] " + ChatColor.GREEN + "Survival 플러그인이 활성화됨");

        loadConfig();

        initEvents();
        initCommands(this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[Survival] " + ChatColor.RED + "Survival 플러그인이 비활성화됨");
    }

    private void initEvents() {
        PluginManager pm = getServer().getPluginManager();
//        pm.registerEvents(new JoinEvent(), this);
//        pm.registerEvents(new PlayerChattedEvent(), this);
    }

    private void loadConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
