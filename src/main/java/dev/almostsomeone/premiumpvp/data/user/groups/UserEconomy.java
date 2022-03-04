package dev.almostsomeone.premiumpvp.data.user.groups;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.DataGroup;
import dev.almostsomeone.premiumpvp.data.DataObject;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class UserEconomy extends DataGroup {

    private DataObject coins;

    public UserEconomy(DataContainer dataContainer) {
        super(dataContainer);

        this.coins = new DataObject(this, "Coins", 0);
    }

    @Override
    public void load() {
        super.load();
        if(this.resultSet == null) return;
        try {
            this.coins.setInteger(this.resultSet.getInt("Coins"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public @NotNull String tableName() {
        return "economy";
    }

    @Override
    public boolean inDatabase() {
        return true;
    }

    public Integer getCoins() {
        return this.coins.getInteger();
    }
}
