package io.github.imtotem.corpse.command;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.kyori.adventure.text.Component.text;

public class CorpseCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ( sender instanceof Player ) {
            if ( args.length == 1 ) {
                List<NPC> npcs = new ArrayList<>();
                Set<String> nameSet = new HashSet<>();
                CitizensAPI.getNPCRegistry().forEach(npc -> {
                    npcs.add(npc);
                    nameSet.add(npc.getName());
                });

                if ( nameSet.contains(args[0]) ) {
                    List<NPC> corpseList = npcs.stream().filter(npc -> npc.getName().equalsIgnoreCase(args[0])).toList();
                    sender.sendMessage(text("[Corpse List] > Total: ", NamedTextColor.GREEN)
                            .append(text(corpseList.size(), NamedTextColor.GOLD)));
                    for (int i = 0; i < corpseList.size(); i++) {
                        Location loc = corpseList.get(i).getStoredLocation();
                        StringBuilder sb = new StringBuilder()
                                .append("X:").append(loc.getBlockX())
                                .append(", Y:").append(loc.getBlockY())
                                .append(", Z:").append(loc.getBlockZ())
                                .append(", World:").append(loc.getWorld().getName());
                        TextComponent component = text((i+1)+". [")
                                .append(text(sb.toString(), NamedTextColor.GREEN))
                                .append(text("]"))
                                .hoverEvent(HoverEvent.showText(text("Click to copy")))
                                .clickEvent(ClickEvent.copyToClipboard(sb.toString()));
                        sender.sendMessage(component);
                    }
                    return true;
                }
                else if ( args[0].equalsIgnoreCase("list") && nameSet.size() > 0 ) {
                    sender.sendMessage(text("[Corpse List] > Total: ", NamedTextColor.GREEN)
                            .append(text(nameSet.size(), NamedTextColor.GOLD)));
                    sender.sendMessage(text(nameSet.stream().toList().toString()));

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
        Set<String> nameSet = new HashSet<>();
        CitizensAPI.getNPCRegistry().forEach(npc -> nameSet.add(npc.getName()));
        list.addAll(nameSet);

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