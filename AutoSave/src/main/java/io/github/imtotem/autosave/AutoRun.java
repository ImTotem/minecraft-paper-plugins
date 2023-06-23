package io.github.imtotem.autosave;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.world.WorldSaveEvent;

import static net.kyori.adventure.text.Component.text;

public class AutoRun {
    public static void run(AutoSave plugin) {
        if ( plugin.getId() != null ) plugin.getServer().getScheduler().cancelTask(plugin.getId());

        int id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            plugin.getServer().broadcast(text("게임을 저장하는 중입니다 (시간이 걸리 수도 있습니다!)"));
            dispatchCommand(plugin);
            plugin.getServer().broadcast(text("게임을 저장했습니다"));
        }, 60L, plugin.getConfig().getLong("ticks"));

        plugin.setId(id);
    }

    public static void dispatchCommand(AutoSave plugin) {
        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "save-all");

        for ( World world : plugin.getServer().getWorlds() )
            plugin.getServer().getPluginManager().callEvent(new WorldSaveEvent(world));
    }
}
