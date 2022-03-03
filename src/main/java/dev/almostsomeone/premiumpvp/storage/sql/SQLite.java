package dev.almostsomeone.premiumpvp.storage.sql;

import com.zaxxer.hikari.HikariConfig;

import java.io.File;

public class SQLite extends SQL {

    public SQLite(File file){
        this.hikariConfig = new HikariConfig();
        this.hikariConfig.setDataSourceClassName("org.sqlite.SQLiteDataSource");
        this.hikariConfig.setJdbcUrl("jdbc:sqlite:" + file);
    }
}
