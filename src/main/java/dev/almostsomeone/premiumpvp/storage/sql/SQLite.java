package dev.almostsomeone.premiumpvp.storage.sql;

import com.zaxxer.hikari.HikariConfig;

import java.io.File;

public class SQLite extends SQL {

    public SQLite(File file){
        hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.sqlite.JDBC");
        hikariConfig.setJdbcUrl("jdbc:sqlite:" + file);
    }
}
