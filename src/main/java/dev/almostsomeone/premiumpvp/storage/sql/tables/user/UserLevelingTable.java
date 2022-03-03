package dev.almostsomeone.premiumpvp.storage.sql.tables.user;

import dev.almostsomeone.premiumpvp.storage.sql.tables.TableBuilder;

import java.util.Arrays;

public class UserLevelingTable extends TableBuilder {

    public UserLevelingTable() {
        super("user_leveling", Arrays.asList(
                "UUID varchar(255) NOT NULL",
                "Level int DEFAULT 1",
                "Experience int DEFAULT 0",
                "CONSTRAINT PK_Leveling PRIMARY KEY (UUID)"
        ));
    }
}
