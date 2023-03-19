package dev.almostsomeone.premiumpvp.data.users;

import javax.annotation.Nonnull;
import java.util.UUID;

public class User {

    private final UUID uuid;

    User(@Nonnull UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return uuid;
    }
}
