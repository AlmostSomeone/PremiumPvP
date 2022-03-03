package dev.almostsomeone.premiumpvp.storage.sql.tables;

import com.zaxxer.hikari.HikariDataSource;
import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TableBuilder {

    private String sqlQuery;

    public TableBuilder(String tableName, List<String> columns) {
        YamlConfiguration config = Main.getInstance().config.get();
        String prefix = (config.isSet("mysql.prefix") ? config.getString("mysql.prefix").length() > 0 ? config.getString("mysql.prefix") + "_" : "" : "premiumpvp_");
        String colText = columns.toString().replaceAll("\\[", "").replaceAll("\\]", "");
        this.sqlQuery = "CREATE TABLE IF NOT EXISTS `" + prefix + tableName + "` " +
        "(" +
                colText +
        ");";
    }

    public void create(HikariDataSource dataSource) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(this.sqlQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignored) {}
        }
    }
}
