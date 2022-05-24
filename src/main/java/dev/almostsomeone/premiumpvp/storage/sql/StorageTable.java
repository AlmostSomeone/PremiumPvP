package dev.almostsomeone.premiumpvp.storage.sql;

import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.logging.Level;

public record StorageTable(String tableName) {

    public StorageTable(String tableName) {
        FileConfiguration config = Main.getInstance().getConfig();
        this.tableName = (config.isSet("storage.prefix") && config.getString("storage.prefix").length() > 0 ? config.getString("storage.prefix") : "ppvp") + "_" + tableName;
    }

    public boolean doesExist() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Main.getInstance().getStorage().getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, tableName, null);
            if (tables.next())
                return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException ignored) {
            }
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (SQLException ignored) {
            }
        }
        return false;
    }

    public ResultSet executeQuery(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Main.getInstance().getStorage().getConnection();
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException ignored) {
            }
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (SQLException ignored) {
            }
        }
        return null;
    }

    public void executeUpdate(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Main.getInstance().getStorage().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException ignored) {
            }
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public String getTableName() {
        return this.tableName;
    }
}
