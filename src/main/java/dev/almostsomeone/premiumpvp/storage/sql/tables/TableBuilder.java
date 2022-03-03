package dev.almostsomeone.premiumpvp.storage.sql.tables;

import com.zaxxer.hikari.HikariDataSource;
import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableBuilder {

    private final String createQuery;

    private final String tableName;
    private final List<String> columns;

    public TableBuilder(String tableName, List<String> columns) {
        this.columns = columns;

        YamlConfiguration config = Main.getInstance().config.get();
        this.tableName = (config.isSet("mysql.prefix") ? config.getString("mysql.prefix").length() > 0 ? config.getString("mysql.prefix") + "_" : "" : "premiumpvp_") + tableName;
        String colText = columns.toString().replaceAll("\\[", "").replaceAll("\\]", "");
        this.createQuery = "CREATE TABLE IF NOT EXISTS `" + this.tableName + "` " +
        "(" +
                colText +
        ");";
    }

    public void create(HikariDataSource dataSource) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(this.createQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignored) {}
        }
    }

    public String getTableName() {
        return this.tableName;
    }
}
