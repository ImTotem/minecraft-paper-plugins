package com.imtotem.chatsound.command;

import com.imtotem.chatsound.Chatsound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatSoundCommand implements CommandExecutor, TabCompleter {
    private final Plugin instance = Chatsound.getPlugin(Chatsound.class);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if ( sender instanceof Player ) {
            if ( args.length == 0 ) {
                Player player = (Player) sender;
                String uuid = player.getUniqueId().toString();
                boolean status = instance.getConfig().getBoolean("uuid." + uuid);
                String stringStatus = status ? "On" : "Off";
                TextColor color = status ? NamedTextColor.GREEN : Chatsound.offHexColor;

                player.sendMessage(
                        Component.text("Chat Sound", Chatsound.baseHexColor)
                                .append(Component.text(": "))
                                .append(Component.text(stringStatus, color))
                );
                return true;
            }
            else if ( args.length == 1 ) {
                Player player = (Player) sender;
                String uuid = player.getUniqueId().toString();

                if ( args[0].equalsIgnoreCase("on") ) {
                    if ( instance.getConfig().getBoolean("uuid." + uuid) ) {
                        player.sendMessage(
                                ChatColor.RED + "Already" + ChatColor.GREEN + " On"
                        );
                    }
                    else {
                        player.sendMessage(
                                Component.text("Chat Sound", Chatsound.baseHexColor)
                                        .append(Component.text(": "))
                                        .append(Component.text("On", NamedTextColor.GREEN))
                        );
                        instance.getConfig().set("uuid." + uuid, true);
                        instance.saveConfig();
                    }

                    return true;
                }
                else if ( args[0].equalsIgnoreCase("off") ) {
                    if ( !instance.getConfig().getBoolean("uuid." + uuid) ) {
                        player.sendMessage(
                                Component.text("Already", NamedTextColor.RED)
                                        .append(Component.text(" Off", Chatsound.offHexColor))
                        );
                    }
                    else {
                        player.sendMessage(
                                Component.text("Chat Sound", Chatsound.baseHexColor)
                                        .append(Component.text(": "))
                                        .append(Component.text("Off", Chatsound.offHexColor))
                        );
                        instance.getConfig().set("uuid." + uuid, false);
                        instance.saveConfig();
                    }

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {

        List<String> list = new ArrayList<>(Arrays.asList("on", "off"));

        if ( command.getName().equalsIgnoreCase("chatsound") ) {
            if ( args.length == 0 ) {
                return list;
            }
            else if ( args.length == 1 ) {
                List<String> filtered_list = new ArrayList<>();
                for ( String str : list ) {
                    if (str.length() >= args[0].length()) {
                        if (str.substring(0, args[0].length()).equalsIgnoreCase(args[0])) {
                            filtered_list.add(str);
                        }
                    }
                }
                return filtered_list;
            }
            else {
                return new ArrayList<>();
            }
        }

        return new ArrayList<>();
    }
}