package dev.almostsomeone.premiumpvp.data.kit;

import dev.almostsomeone.premiumpvp.data.DataContainer;

import javax.annotation.Nonnull;
import java.util.UUID;

public class Kit extends DataContainer {

    public Kit(final UUID uuid) {
        super(uuid);
    }

    @Override
    public @Nonnull String tablePrefix() {
        return "Kit";
    }
}
