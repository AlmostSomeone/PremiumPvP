package dev.almostsomeone.premiumpvp.data.objects;

import dev.almostsomeone.premiumpvp.data.objects.user.UserLeveling;

import java.util.UUID;

public class UserData {

    private final UUID uuid;

    private UserLeveling userLeveling;

    public UserData(final UUID uuid) {
        this.uuid = uuid;
    }

    public void save() {
        this.userLeveling.save();
    }

    public UserLeveling getUserLeveling() {
        return this.userLeveling;
    }

    public void loadUserLeveling() {
        this.userLeveling = new UserLeveling(this.uuid);
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
