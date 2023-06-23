package com.imtotem.chatsound;

import com.imtotem.chatsound.command.ChatSoundCommand;
import com.imtotem.chatsound.event.JoinEvent;
import com.imtotem.chatsound.event.PlayerChattedEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Chatsound extends JavaPlugin {

    public static TextColor baseHexColor = TextColor.fromHexString("#00b3ff");
    public static TextColor offHexColor = TextColor.fromHexString("#fa6464");

    @Override
    public void onEnable()
    {
        Bukkit.getConsoleSender().sendMessage("[ChatSound] " + ChatColor.GREEN + "ChatSound 플러그인이 활성화됨");

        initEvents();
        initCommands();

        loadConfig();
    }

    @Override
    public void onDisable()
    {
        Bukkit.getConsoleSender().sendMessage("[ChatSound] " + ChatColor.RED + "ChatSound 플러그인이 비활성화됨");
    }

    public void initEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new PlayerChattedEvent(), this);
    }

    public void initCommands() {
        Objects.requireNonNull(getCommand("chatsound")).setExecutor(new ChatSoundCommand());
        Objects.requireNonNull(getCommand("chatsound")).setTabCompleter(new ChatSoundCommand());
    }

    public void loadConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
