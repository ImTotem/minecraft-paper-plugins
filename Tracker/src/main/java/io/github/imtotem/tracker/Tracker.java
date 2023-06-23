package io.github.imtotem.tracker;

import io.github.imtotem.tracker.actionbar.ActionBarThread;
import io.github.imtotem.tracker.command.PlaceCommand;
import io.github.imtotem.tracker.command.ToggleCommand;
import io.github.imtotem.tracker.command.TrakcerCommand;
import io.github.imtotem.tracker.command.tabcompleter.PlaceCommandTabCompleter;
import io.github.imtotem.tracker.command.tabcompleter.TrackerCommandTabCompleter;
import io.github.imtotem.tracker.event.DeathEvent;
import io.github.imtotem.tracker.event.JoinAQuitEvent;
import io.github.imtotem.tracker.manager.ToggleManager;
import io.github.imtotem.tracker.versions.ActionBar;
import io.github.imtotem.tracker.versions.ActionBar_1_19_R1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Tracker extends JavaPlugin {

    public ToggleManager toggleManager;

    public static ActionBar actionBar;
    public static Target target;
    public static HashMap<String, Location> deadLocation = new HashMap<>();
    ActionBarThread thread = new ActionBarThread();

    public String sversion;

    @Override
    public void onEnable() {

        if ( !setupActionBar() ) {
            getLogger().severe("Failed to setup Tracker! Running non-compatible version!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getConsoleSender().sendMessage("[Tracker] " + ChatColor.GREEN + "Tracker 플러그인이 활성화됨");

        this.toggleManager = new ToggleManager(this);

        initEvents();
        initCommands();
        loadConfig();

        target = new Target();
        for (Player p : Bukkit.getOnlinePlayers()) {
            String name = p.getName();
            target.injectTarget(name, name);
            if ( toggleManager.getConfig().get(name) == null ) {
                toggleManager.getConfig().set(name, false);
                toggleManager.saveConfig();
            }
        }

        thread.runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[Tracker] " + ChatColor.RED + "Tracker 플러그인이 비활성화됨");
    }

    public void initEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinAQuitEvent(), this);
        pm.registerEvents(new DeathEvent(), this);
    }

    public void initCommands() {
        PluginCommand command_target = getCommand("target");
        if ( command_target != null ) {
            command_target.setExecutor(new TrakcerCommand());
            command_target.setTabCompleter(new TrackerCommandTabCompleter());
        }

        PluginCommand command_place = getCommand("place");
        if ( command_place != null ) {
            command_place.setExecutor(new PlaceCommand());
            command_place.setTabCompleter(new PlaceCommandTabCompleter());
        }

        PluginCommand command_toggle = getCommand("toggle");
        if ( command_toggle != null ) {
            command_toggle.setExecutor(new ToggleCommand());
        }
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private boolean setupActionBar() {
        sversion = "N/A";

        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        switch (sversion) {
            case "v1_19_R1":
            case "v1_20_R1":
                actionBar = new ActionBar_1_19_R1();
                break;
        }

        return actionBar != null;
    }
}
