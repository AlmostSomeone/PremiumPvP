package dev.almostsomeone.premiumpvp.data.user;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.user.groups.UserEconomy;
import dev.almostsomeone.premiumpvp.data.user.groups.UserLeveling;
import dev.almostsomeone.premiumpvp.data.user.groups.UserStatistics;

import javax.annotation.Nonnull;
import java.util.UUID;

public class User extends DataContainer {

    private final UserLeveling userLeveling;
    private final UserEconomy userEconomy;
    private final UserStatistics userStatistics;

    public User(final UUID uuid) {
        super(uuid);
        userLeveling = new UserLeveling(this);
        userEconomy = new UserEconomy(this);
        userStatistics = new UserStatistics(this);
    }

    @Override
    public @Nonnull String tablePrefix() {
        return "player";
    }

    public UserLeveling getLeveling() {
        return userLeveling;
    }

    public UserEconomy getEconomy() {
        return userEconomy;
    }

    public UserStatistics getStatistics() {
        return userStatistics;
    }
}
