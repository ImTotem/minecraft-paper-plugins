package io.github.imtotem.tracker.command.tabcompleter;

import io.github.imtotem.tracker.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TrackerCommandTabCompleter implements TabCompleter {
    private final Plugin instance = Tracker.getPlugin(Tracker.class);

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> list = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) { list.add(p.getName()); }
        list.addAll(instance.getConfig().getKeys(false));

        if ( command.getName().equalsIgnoreCase("target") ) {
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
