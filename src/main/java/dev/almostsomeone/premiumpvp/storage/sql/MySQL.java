package dev.almostsomeone.premiumpvp.storage.sql;

import com.zaxxer.hikari.HikariConfig;

public class MySQL extends SQL {

    public MySQL(String host, String port, String database, String username, String password){
        this.hikariConfig = new HikariConfig();
        this.hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        this.hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);

        this.hikariConfig.setUsername(username);
        this.hikariConfig.setPassword(password);

        this.hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        this.hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        this.hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        this.hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        this.hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        this.hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        this.hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }
}
