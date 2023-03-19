package dev.almostsomeone.premiumpvp.storage.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SQL {

    protected HikariConfig hikariConfig;
    private HikariDataSource hikariDataSource;

    public void setupPool() {
        hikariConfig.setPoolName("ppvp-pool");
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setConnectionTimeout(20000);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setConnectionTestQuery("SELECT 1;");
        hikariConfig.setAutoCommit(false);

        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public void closePool() {
        if (hikariDataSource != null && !hikariDataSource.isClosed()) hikariDataSource.close();
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}
