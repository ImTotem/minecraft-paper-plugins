package io.github.imtotem.corpse.corpse;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RightClickCorpse extends Event {

    private final Player player;
    private final Corpse corpse;

    private static final HandlerList HANDLERS = new HandlerList();

    public RightClickCorpse(Player player, Corpse corpse) {
        this.player = player;
        this.corpse = corpse;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public Corpse getCorpse() {
        return corpse;
    }
}
