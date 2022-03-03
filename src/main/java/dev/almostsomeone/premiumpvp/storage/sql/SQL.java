package dev.almostsomeone.premiumpvp.storage.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.almostsomeone.premiumpvp.storage.sql.tables.TableBuilder;
import dev.almostsomeone.premiumpvp.storage.sql.tables.user.UserLevelingTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SQL {

    protected HikariConfig hikariConfig;
    private HikariDataSource hikariDataSource;

    private List<TableBuilder> tables = Arrays.asList(
            new UserLevelingTable()
    );

    public void setupPool() {
        this.hikariConfig.setMinimumIdle(10);
        this.hikariConfig.setMaximumPoolSize(10);
        this.hikariConfig.setConnectionTimeout(20000);
        this.hikariConfig.setIdleTimeout(10000);
        this.hikariConfig.setMaxLifetime(1000);

        this.hikariDataSource = new HikariDataSource(this.hikariConfig);
    }

    public void closePool() {
        if(this.hikariDataSource != null && !this.hikariDataSource.isClosed())
            this.hikariDataSource.close();
    }

    public void createTables(){
        for(TableBuilder table : this.tables)
           this.createTable(table);
    }

    private void createTable(TableBuilder tableBuilder) {
        tableBuilder.create(this.hikariDataSource);
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}
