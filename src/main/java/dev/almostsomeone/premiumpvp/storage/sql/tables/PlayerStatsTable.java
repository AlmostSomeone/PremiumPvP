package dev.almostsomeone.premiumpvp.storage.sql.tables;

import java.util.Arrays;

public class PlayerStatsTable extends TableBuilder {

    public PlayerStatsTable() {
        super("PlayerStats", Arrays.asList(
                "UUID varchar(30)",
                "NAME varchar(30)"
        ));
    }
}
