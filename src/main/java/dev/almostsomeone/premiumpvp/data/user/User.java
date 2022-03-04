package dev.almostsomeone.premiumpvp.data.user;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.user.groups.UserEconomy;
import dev.almostsomeone.premiumpvp.data.user.groups.UserLeveling;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class User extends DataContainer {

    private UserLeveling userLeveling;
    private UserEconomy userEconomy;

    public User(final UUID uuid) {
        super(uuid);
        this.userLeveling = new UserLeveling(this);
        this.userEconomy = new UserEconomy(this);
    }

    @Override
    public @NotNull String tablePrefix() {
        return "player";
    }

    public UserLeveling getLeveling() {
        return this.userLeveling;
    }

    public UserEconomy getEconomy() {
        return this.userEconomy;
    }
}
