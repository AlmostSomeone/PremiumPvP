package dev.almostsomeone.premiumpvp.storage.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SQL {

    protected HikariConfig hikariConfig;
    private HikariDataSource hikariDataSource;

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

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}
