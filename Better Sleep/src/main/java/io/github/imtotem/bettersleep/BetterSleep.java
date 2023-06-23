package io.github.imtotem.bettersleep;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterSleep extends JavaPlugin {

    @Override
    public void onEnable() {
        for (World world : getServer().getWorlds()) {
            if ( world.getEnvironment() == World.Environment.NORMAL ) {
                world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, -1);
            }
        }
    }

    @Override
    public void onDisable() {

    }
}
