package dev.almostsomeone.premiumpvp.storage.sql;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.configuration.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record StorageTable(String tableName) {

    public StorageTable(String tableName) {
        String prefix = Settings.getConfig().getString("storage.prefix", "ppvp");
        this.tableName = (prefix.length() > 0 ? prefix : "ppvp") + "_" + tableName;
    }

    public boolean doesExist() {
        Connection connection = null;
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
        }
        return false;
    }

    public List<Map<String, Object>> executeQuery(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Main.getStorage().getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> row;

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                result.add(row);
            }

            return result;
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
