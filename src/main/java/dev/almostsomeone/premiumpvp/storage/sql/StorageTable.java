package dev.almostsomeone.premiumpvp.storage.sql;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.configuration.Settings;

import java.sql.*;

public record StorageTable(String tableName) {

    public StorageTable(String tableName) {
        String prefix = Settings.getString("storage.prefix", "ppvp");
        this.tableName = (prefix.length() > 0 ? prefix : "ppvp") + "_" + tableName;
    }

    public boolean doesExist() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Main.getStorage().getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, tableName, null);
            if (tables.next()) return true;
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
            connection = Main.getStorage().getConnection();
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
            connection = Main.getStorage().getConnection();
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
}
