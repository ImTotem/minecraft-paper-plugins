package io.github.imtotem.tracker.command;

import io.github.imtotem.tracker.Tracker;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlaceCommand implements CommandExecutor {
    private final Plugin instance = Tracker.getPlugin(Tracker.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if ( sender instanceof Player ) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            location.setX(location.getBlockX()+0.5);
            location.setY(location.getBlockY());
            location.setZ(location.getBlockZ()+0.5);
            location.setYaw(0);
            location.setPitch(0);

            if ( args.length == 1 ) {
                if ( args[0].equalsIgnoreCase("list") ) {
                    if ( instance.getConfig().getKeys(false).isEmpty() ) {
                        player.sendMessage(ChatColor.RED + "Place does not exist.");
                    }
                    else {
                        List<String> strings = new ArrayList<>();
                        for ( String s : instance.getConfig().getKeys(false) ) { strings.add(ChatColor.GREEN + s); }
                        String list = String.join(ChatColor.WHITE+ ", ", strings);
                        player.sendMessage(ChatColor.WHITE + "Places (" + list.split(ChatColor.WHITE + ", ").length +"): " + list);
                    }

                    return true;
                }
            }
            else if ( args.length == 2 ) {
                args[1] = args[1].toLowerCase();
                if ( args[0].equalsIgnoreCase("add") ) {
                    if ( instance.getConfig().getKeys(false).contains(args[1]) ) {
                        player.sendMessage(ChatColor.RED + "Already exists.");
                    }
                    else {
                        instance.getConfig().set(args[1], location);
                        instance.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "Successfully added.");
                    }
                }
                else if ( args[0].equalsIgnoreCase("remove") ) {
                    if ( instance.getConfig().getKeys(false).contains(args[1]) ) {
                        instance.getConfig().set(args[1], null);
                        instance.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "Successfully removed");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "'" + args[1] + "'" + " does not exist.");
                    }
                }
                else if ( args[0].equalsIgnoreCase("modify") ) {
                    if ( instance.getConfig().getKeys(false).contains(args[1]) ) {
                        instance.getConfig().set(args[1], location);
                        instance.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "Successfully modified");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "'" + args[1] + "'" + " does not exist.");
                    }
                }
                else if ( args[0].equalsIgnoreCase("info") ) {
                    if ( instance.getConfig().getKeys(false).contains(args[1])) {
                        Location savedLocation = instance.getConfig().getLocation(args[1]);
                        String[] locate = new String[] {ChatColor.GREEN + "world: " + savedLocation.getWorld().getName(),
                                ChatColor.GREEN + "x: " + savedLocation.getBlockX(),
                                ChatColor.GREEN + "y: " + savedLocation.getBlockY(),
                                ChatColor.GREEN + "z: " + savedLocation.getBlockZ()};
                        player.sendMessage(ChatColor.WHITE + args[1] + ": " + String.join(ChatColor.WHITE + ", ", locate));
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "'" + args[1] + "'" + " does not exist.");
                    }
                }
                return true;
            }
        }
        return false;
    }
}
