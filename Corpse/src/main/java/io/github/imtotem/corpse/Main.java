package io.github.imtotem.corpse;

import io.github.imtotem.corpse.command.CorpseCommand;
import io.github.imtotem.corpse.corpse.CorpseList;
import io.github.imtotem.corpse.corpse.PacketReader;
import io.github.imtotem.corpse.event.CorpseInteract;
import io.github.imtotem.corpse.event.WhenDeadEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static net.kyori.adventure.text.Component.text;

public final class Main extends JavaPlugin {
    // TODO 작업대 인벤 추가
    // TODO 시체 우클할때 시체 우클한 사람 게임모드가 서바이벌, 크리에이티브, 모험모드인지 확인
    @Override
    public void onEnable() {
        loadConfig();

        initCommands();
        initEvents();

        CorpseList.load();

        for ( Player player : Bukkit.getOnlinePlayers() ) {
            new PacketReader(player).inject();
            CorpseList.loadCorpseTo(player);
        }

        Bukkit.getConsoleSender().sendMessage("[Corpse] " + ChatColor.GREEN + "Corpse 플러그인이 활성화됨");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTasks(this);
        CorpseList.save();
        CorpseList.clear();

        Bukkit.getConsoleSender().sendMessage("[Corpse] " + ChatColor.RED + "Corpse 플러그인이 비활성화됨");
    }

    public void initEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new WhenDeadEvent(), this);
        pm.registerEvents(new CorpseInteract(), this);
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
