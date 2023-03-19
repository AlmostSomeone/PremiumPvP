package dev.almostsomeone.premiumpvp.events.user;

import dev.almostsomeone.premiumpvp.data.users.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.UUID;

public class UserLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final UUID uuid;
    private final User user;

    public UserLoadEvent(@Nonnull User user) {
        this.uuid = user.getUniqueId();
        this.user = user;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}