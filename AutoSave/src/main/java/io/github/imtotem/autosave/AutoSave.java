package io.github.imtotem.autosave;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static net.kyori.adventure.text.Component.text;

public final class AutoSave extends JavaPlugin implements Listener {

    private Integer id = null;
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(text("[AutoSave] ").append(text("AutoSave 플러그인이 활성화됨", NamedTextColor.GREEN)));
        loadConfig();

        initCommand();

        AutoRun.run(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTask(id);
        AutoRun.dispatchCommand(this);
        Bukkit.getConsoleSender().sendMessage(text("[AutoSave] ").append(text("AutoSave 플러그인이 비활성화됨", NamedTextColor.RED)));
    }

    private void loadConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void initCommand() {
        Objects.requireNonNull(getCommand("autosave")).setExecutor(new Command(this));
        Objects.requireNonNull(getCommand("autosave")).setTabCompleter(new CommandTabCompleter());
    }

    void setId(int id) {
        this.id = id;
    }

    Integer getId() {
        return this.id;
    }
}
