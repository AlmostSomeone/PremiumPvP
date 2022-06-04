package dev.almostsomeone.premiumpvp.data.user.groups;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.DataGroup;
import dev.almostsomeone.premiumpvp.data.DataObject;

import javax.annotation.Nonnull;
import java.sql.SQLException;

public class UserEconomy extends DataGroup {

    private final DataObject coins;

    public UserEconomy(DataContainer dataContainer) {
        super(dataContainer);
        coins = new DataObject(this, "Coins", 0);
    }

    @Override
    public void load() {
        super.load();
        if(resultSet == null) return;
        try {
            coins.setInteger(resultSet.getInt("Coins"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public @Nonnull String tableName() {
        return "economy";
    }

    @Override
    public boolean inDatabase() {
        return true;
    }

    public Integer getCoins() {
        return coins.getInteger();
    }
}
