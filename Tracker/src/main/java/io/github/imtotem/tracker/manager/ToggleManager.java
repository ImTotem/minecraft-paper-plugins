package io.github.imtotem.tracker.manager;

import io.github.imtotem.tracker.Tracker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ToggleManager {
    
    private final Tracker plugin;
    private FileConfiguration toggleConfig = null;
    private File configFile = null;

    public ToggleManager(Tracker plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if ( this.configFile == null )
            this.configFile = new File(this.plugin.getDataFolder(), "toggle.yml");

        this.toggleConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("toggle.yml");
        if ( defaultStream != null ) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.toggleConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if ( this.toggleConfig == null )
            reloadConfig();

        return this.toggleConfig;
    }

    public void saveConfig() {
        if ( this.toggleConfig == null || this.configFile == null )
            return;
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "toggle.yml");

        if ( !this.configFile.exists() ) {
            this.plugin.saveResource("toggle.yml", false);
        }
    }
}
