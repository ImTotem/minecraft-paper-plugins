package io.github.imtotem.autosave;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if ( command.getName().equalsIgnoreCase("autosave") ) {
            if ( args.length == 0 ) {
                list.add("period");
                return list;
            }
            else if ( args.length == 1 ) {
                list.add("period");
                return filtering(list, args[0]);
            }
            else if ( args.length == 2 && args[1].equalsIgnoreCase("") ) {
                list.add("<number for period>");
                return list;
            }
            else if ( args.length == 3 ) {
                list.add("hour");
                list.add("min");
                list.add("sec");
                return filtering(list, args[2]);
            }
            else {
                return new ArrayList<>();
            }
        }

        return new ArrayList<>();
    }

    private List<String> filtering(List<String> list, String arg) {
        List<String> filtered_list = new ArrayList<>();
        for ( String str : list ) {
            if ( str.length() >= arg.length() ) {
                if (str.substring(0, arg.length()).equalsIgnoreCase(arg)) {
                    filtered_list.add(str);
                }
            }
        }

        return filtered_list;
    }
}
