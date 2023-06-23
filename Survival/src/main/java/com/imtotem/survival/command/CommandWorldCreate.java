package com.imtotem.survival.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.Plugin;

public class CommandWorldCreate {
    public static void create(String name, String environment, String type, long seed) {
        Bukkit.createWorld(
                WorldCreator.name(name)
                        .seed(seed)
                        .environment(World.Environment.valueOf(environment))
                        .type(WorldType.valueOf(type))
        );
    }
}
