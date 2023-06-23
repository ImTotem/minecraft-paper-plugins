package io.github.imtotem.tracker.command;

import io.github.imtotem.tracker.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class TrakcerCommand implements CommandExecutor {

    private final Plugin instance = Tracker.getPlugin(Tracker.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if ( sender instanceof Player ) {
            if ( args.length == 1 ) {
                Player player = Bukkit.getPlayer(args[0]);
                if ( !args[0].equalsIgnoreCase(sender.getName()) ) {
                    if ( player != null && player.isOnline() ) {
                        Tracker.target.injectTarget(sender.getName(), player.getName());
                        sender.sendMessage(ChatColor.GREEN + "Tracked successfully.");
                    }
                    else if ( instance.getConfig().getKeys(false).contains(args[0].toLowerCase()) ) {
                        Tracker.target.injectTarget(sender.getName(), args[0].toLowerCase());
                        sender.sendMessage(ChatColor.GREEN + "Tracked successfully.");
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "You can only track added places or online players.");
                    }
                }
                else if ( instance.getConfig().getKeys(false).contains(args[0].toLowerCase()) ) {
                    Tracker.target.injectTarget(sender.getName(), args[0].toLowerCase());
                    sender.sendMessage(ChatColor.GREEN + "Tracked successfully.");
                }
                else {
                    sender.sendMessage(ChatColor.RED + "You can't track yourself.");
                }

                return true;
            }
        }

        return false;
    }
}
