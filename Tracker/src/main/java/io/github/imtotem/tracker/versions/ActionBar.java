package io.github.imtotem.tracker.versions;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

public interface ActionBar {
    public void sendActionBar(Player player, TextComponent message);

}
