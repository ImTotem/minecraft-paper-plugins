package io.github.imtotem.tracker.command.tabcompleter;

import io.github.imtotem.tracker.Tracker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceCommandTabCompleter implements TabCompleter {
    private final Plugin instance = Tracker.getPlugin(Tracker.class);

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("add", "remove", "modify", "list", "info"));
        List<String> list2 = new ArrayList<>(instance.getConfig().getKeys(false));

        if ( command.getName().equalsIgnoreCase("place") ) {
            if ( args.length == 0 ) {
                return list;
            }
            else if ( args.length == 1) {
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
            else if ( args.length == 2 ) {
                if ( args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("add") ) return new ArrayList<>();
                List<String> filtered_list = new ArrayList<>();
                for ( String str : list2 ) {
                    if (str.length() >= args[1].length()) {
                        if (str.substring(0, args[1].length()).equalsIgnoreCase(args[1])) {
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
