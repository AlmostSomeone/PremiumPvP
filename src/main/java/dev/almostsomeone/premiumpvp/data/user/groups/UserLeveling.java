package dev.almostsomeone.premiumpvp.data.user.groups;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.DataGroup;
import dev.almostsomeone.premiumpvp.data.DataObject;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class UserLeveling extends DataGroup {

    private DataObject level, experience;

    public UserLeveling(DataContainer dataContainer) {
        super(dataContainer);

        level = new DataObject(this, "Level", 1);
        experience = new DataObject(this, "Experience", 0);
    }

    @Override
    public void load() {
        super.load();
        if(this.resultSet == null) return;
        try {
            this.level.setInteger(this.resultSet.getInt("Level"));
            this.experience.setInteger(this.resultSet.getInt("Experience"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public @NotNull String tableName() {
        return "leveling";
    }

    @Override
    public boolean inDatabase() {
        return true;
    }

    public Integer getLevel() {
        return this.level.getInteger();
    }

    public Integer getExperience() {
        return this.experience.getInteger();
    }
}
