package io.github.imtotem.corpse.command;

import io.github.imtotem.corpse.corpse.Corpse;
import io.github.imtotem.corpse.corpse.CorpseList;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class CorpseCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ( sender instanceof Player) {
            List<String> nameList = CorpseList.getNameList();
            if ( args.length == 1 ) {
                if ( nameList.contains(args[0]) ) {
                    List<Corpse> corpseList = CorpseList.getByName(args[0]);
                    sender.sendMessage(text("[Corpse List] > Total: ", NamedTextColor.GREEN)
                            .append(text(corpseList.size(), NamedTextColor.GOLD)));
                    for (int i = 0; i < corpseList.size(); i++) {
                        Location loc = corpseList.get(i).getCorpseData().getLocation();
                        StringBuilder sb = new StringBuilder()
                                .append("X:").append(loc.getBlockX())
                                .append(", Y:").append(loc.getBlockY())
                                .append(", Z:").append(loc.getBlockZ())
                                .append(", World:").append(loc.getWorld().getName());
                        TextComponent component = text((i+1)+". [" + ChatColor.GREEN + sb + ChatColor.RESET + "]")
                                .hoverEvent(HoverEvent.showText(text("Click to copy")))
                                .clickEvent(ClickEvent.copyToClipboard(sb.toString()));
                        sender.sendMessage(component);
                    }
                    return true;
                }
                else if ( args[0].equalsIgnoreCase("list") && nameList.size() > 0 ) {
                    sender.sendMessage(text("[Corpse List] > Total: ", NamedTextColor.GREEN)
                            .append(text(nameList.size(), NamedTextColor.GOLD)));
                    sender.sendMessage(nameList.toString());

                    return true;
                } else {
                    sender.sendMessage(text("No have any Corpse", NamedTextColor.RED));
                    return true;
                }
            }
        } else {
            sender.sendMessage(text("Only Player can use this command", NamedTextColor.RED));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {

        List<String> list = new ArrayList<>(List.of("list"));
        list.addAll(CorpseList.getNameList());

        if ( command.getName().equalsIgnoreCase("corpse") ) {
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
