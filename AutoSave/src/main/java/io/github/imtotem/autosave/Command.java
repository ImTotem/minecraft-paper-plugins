package io.github.imtotem.autosave;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class Command implements CommandExecutor {
    protected final Plugin plugin;

    public Command(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ( 0 < args.length && args[0].equalsIgnoreCase("period")) {
            if ( args.length == 1 ) {
                double number = plugin.getConfig().getDouble("period");
                String unit = plugin.getConfig().getString("unit");

                sender.sendMessage(text("period: " + number + unit, NamedTextColor.GRAY));
                return true;
            }
            else {
                double number = Double.parseDouble(args[1]);
                switch (args.length) {
                    case 2 -> {
                        sender.sendMessage(changePeriod(number));
                        return true;
                    }
                    case 3 -> {
                        sender.sendMessage(changePeriod(number, args[2]));
                        return true;
                    }
                    default -> {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public TextComponent changePeriod(double number) {
        return changePeriod(number, "tick");
    }

    public TextComponent changePeriod(double number, String unit) {
        int intUnit;
        switch (unit.toLowerCase()) {
            case "hour" -> intUnit = 7200;
            case "min" -> intUnit = 1200;
            case "sec" -> intUnit = 20;
            default -> intUnit = 1;
        }

        long ticks = (long) ( number * intUnit );

        double prevPeriod = plugin.getConfig().getDouble("period");
        String prevUnit = plugin.getConfig().getString("unit");

        plugin.getConfig().set("period", number);
        plugin.getConfig().set("unit", unit);
        plugin.getConfig().set("ticks", ticks);
        plugin.saveConfig();

        AutoRun.run((AutoSave) plugin);
        return text("period changed " + prevPeriod + prevUnit + " -> " + number + unit, NamedTextColor.GREEN);
    }
}
