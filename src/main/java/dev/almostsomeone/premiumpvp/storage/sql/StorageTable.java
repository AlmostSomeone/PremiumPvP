package dev.almostsomeone.premiumpvp.storage.sql;

import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.sql.*;

public record StorageTable(String tableName) {

    public StorageTable(String tableName) {
        YamlConfiguration config = Main.getInstance().config.get();
        this.tableName = (config.isSet("mysql.prefix") ? config.getString("mysql.prefix").length() > 0 ? config.getString("mysql.prefix") + "_" : "" : "premiumpvp_") + tableName;
    }

    public Boolean doesExist() {
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

    public Integer executeUpdate(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Main.getInstance().getStorage().getConnection();
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate();
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

    public String getTableName() {
        return this.tableName;
    }
}
