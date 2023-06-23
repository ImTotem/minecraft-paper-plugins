package io.github.imtotem.tracker.command;

import io.github.imtotem.tracker.Tracker;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class ToggleCommand implements CommandExecutor {
    private final Tracker instance = Tracker.getPlugin(Tracker.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if ( sender instanceof Player) {
            if ( args.length == 0 ) {
                Player player = (Player) sender;

                boolean toggle = instance.toggleManager.getConfig().getBoolean(player.getName());
                instance.toggleManager.getConfig().set(player.getName(), !toggle);
                instance.toggleManager.saveConfig();

                String state = (!toggle) ? "On" : "Off";
                NamedTextColor chatColor = (!toggle) ? NamedTextColor.GREEN : NamedTextColor.RED;
                TextComponent message = text("Tracker", TextColor.fromHexString("#00b3ff"))
                        .append(text(": "))
                        .append(text(state, chatColor));

                player.sendMessage(message);

                return true;
            }
        }

        return false;
    }
}
