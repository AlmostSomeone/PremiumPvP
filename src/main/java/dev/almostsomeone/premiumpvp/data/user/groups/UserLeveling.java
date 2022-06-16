package dev.almostsomeone.premiumpvp.data.user.groups;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.DataGroup;
import dev.almostsomeone.premiumpvp.data.DataObject;

import javax.annotation.Nonnull;

public class UserLeveling extends DataGroup {

    private final DataObject level, experience;

    public UserLeveling(DataContainer dataContainer) {
        super(dataContainer);
        level = new DataObject(this, "Level", 1);
        experience = new DataObject(this, "Experience", 0);
    }

    @Override
    public @Nonnull String tableName() {
        return "leveling";
    }

    @Override
    public boolean inDatabase() {
        return true;
    }

    public Integer getLevel() {
        return level.getInteger();
    }

    public Integer getExperience() {
        return experience.getInteger();
    }
}
