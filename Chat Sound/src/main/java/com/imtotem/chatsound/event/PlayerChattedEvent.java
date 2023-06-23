package com.imtotem.chatsound.event;

import com.imtotem.chatsound.Chatsound;
import com.imtotem.chatsound.util.FilteringCommand;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PlayerChattedEvent implements Listener {
    private final Plugin instance = Chatsound.getPlugin(Chatsound.class);

    FilteringCommand fc = new FilteringCommand();

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        filteringPlayer(player).forEach(this::playSound);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        String command = event.getMessage();
        filteringPlayer(player, command).forEach(this::playSound);
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        CommandSender sender = event.getSender();

        String command = event.getCommand().toLowerCase();

        filteringPlayer(sender, command).forEach(this::playSound);
    }

    public Collection<? extends Player> filteringPlayer(CommandSender sender, String command) {
        Collection<Player> collection = new HashSet<>();
        command = command.toLowerCase();

        String uuid = "";

        if ( sender instanceof Player ) {
            command = command.substring(1);
            uuid = ((Player) sender).getUniqueId().toString();
        }

        final String finalUUID = uuid;
        if ( sender instanceof Player || sender instanceof ConsoleCommandSender) {
            if ( startsWith(command, fc.getACommand()) ) {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if ( checkChatSoundIsOn(p, finalUUID) ) collection.add(p);
                });

            } else if ( startsWith(command, fc.getTCommand()) ) {
                String arg = command.substring(command.indexOf(" ")+1);
                if ( arg.startsWith("@") ) {
                    boolean play = true;
                    List<Entity> selectedEntities = Bukkit.selectEntities(sender, findSelector(arg));
                    for ( Entity entity : selectedEntities ) {
                        if ( !(entity instanceof Player) ) {
                            play = false;
                            break;
                        }
                    }
                    if (play) {
                        selectedEntities.forEach(entity -> {
                            Player p = (Player) entity;
                            if ( checkChatSoundIsOn(p, finalUUID) ) collection.add(p);
                        });
                    }

                } else {
                    Player p = Bukkit.getPlayer(arg.split(" ")[0]);
                    if ( p != null && p.isOnline() && checkChatSoundIsOn(p, uuid) ) collection.add(p);
                }
            }

        }

        return collection;
    }

    private Collection<? extends Player> filteringPlayer(Player player) {
        Collection<Player> collection = new HashSet<>();

        String uuid = player.getUniqueId().toString();

        Bukkit.getOnlinePlayers().forEach(p -> {
            if ( checkChatSoundIsOn(p, uuid) ) collection.add(p);
        });

        return collection;
    }

    private boolean checkChatSoundIsOn(Player player, String uuid) {
        String key = player.getUniqueId().toString();
        return !(key.equals(uuid)) && instance.getConfig().getBoolean("uuid." + key);
    }

    private boolean startsWith(String target, String[] prefix) {
        boolean ret = false;

        for (String pref : prefix) {
            if (target.startsWith(pref)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    private String findSelector(String cmd) {
        String selector = "";

        String[] split = cmd.split(" ");

        if ( split[0].length() == 2 ) {
            selector = split[0];
        } else {
            char[] arr = cmd.toCharArray();
            int cnt = 1;
            for ( int i = 3; i < arr.length; i ++ ) {
                if ( arr[i] == '[' ) cnt ++;
                if ( arr[i] == ']' ) cnt --;

                if ( cnt == 0 ) {
                    selector = cmd.substring(0, i+1);
                    break;
                }
            }
        }

        return selector;
    }

    private void playSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
    }
}
