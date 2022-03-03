package dev.almostsomeone.premiumpvp.data.objects.user;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.storage.Storage;
import dev.almostsomeone.premiumpvp.storage.sql.tables.user.UserLevelingTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserLeveling {

    private final UUID uuid;
    private Integer level;
    private Integer experience;

    public UserLeveling(final UUID uuid) {
        this.uuid = uuid;
        load();
    }

    private void create() {
        this.level = 1;
        this.experience = 0;

        String tableName = new UserLevelingTable().getTableName();
        String query = "INSERT INTO `" + tableName + "` (`UUID`) VALUES ('" + this.uuid.toString() + "');";

        Storage storage = Main.getInstance().getStorage();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = storage.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignored) {}
        }
    }

    private void load() {
        String tableName = new UserLevelingTable().getTableName();
        String query = "SELECT `Level`, `Experience` FROM `" + tableName + "` WHERE `UUID` = '" + this.uuid.toString() + "';";

        Storage storage = Main.getInstance().getStorage();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = storage.getConnection();
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next() || resultSet.wasNull()) {
                create();
            } else {
                this.level = resultSet.getInt("Level");
                this.experience = resultSet.getInt("Experience");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignored) {}
        }
    }

    public void save(){
        String tableName = new UserLevelingTable().getTableName();
        String query = "UPDATE `" + tableName + "` SET `Level` = " + this.level + ", `Experience` = " + this.experience + " WHERE `UUID` = '" + this.uuid.toString() + "';";

        Storage storage = Main.getInstance().getStorage();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = storage.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignored) {}
        }
    }

    public Integer getLevel() {
        return this.level;
    }

    public Integer getExperience() {
        return this.experience;
    }
}
