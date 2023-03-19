package dev.almostsomeone.premiumpvp.data.kits;

import javax.annotation.Nonnull;
import java.util.UUID;

public class Kit {

    private final UUID uuid;
    private String name;

    Kit(@Nonnull String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
