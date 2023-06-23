package com.imtotem.survival.command;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.kyori.adventure.text.Component.text;

public class CommandWorldInfo {
    public static void info(Player player) {
        player.sendMessage(player.getWorld().getName());
    }

    public static void info(CommandSender sender, String name) {
        sender.sendMessage(text(name));

        sender.sendMessage(
                sender.getServer().getWorlds().stream().map(World::getName).toList().contains(name) ?
                        sender.getServer().getWorld(name).toString() : "NULL"
        );
    }
}
