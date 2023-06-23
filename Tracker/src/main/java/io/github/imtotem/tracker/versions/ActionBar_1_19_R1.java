package io.github.imtotem.tracker.versions;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

public class ActionBar_1_19_R1 implements ActionBar{
    @Override
    public void sendActionBar(Player player, TextComponent message) {
        player.sendActionBar(message);
    }
}
