package dev.almostsomeone.premiumpvp.data;

import dev.almostsomeone.premiumpvp.storage.sql.StorageTable;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DataGroup {

    private DataContainer dataContainer;
    protected List<DataObject> dataObjects;

    protected ResultSet resultSet = null;

    public DataGroup(@NotNull DataContainer dataContainer){
        this.dataObjects = new ArrayList<>();
        dataContainer.dataGroups.add(this);
        this.dataContainer = dataContainer;
    }

    @NotNull
    public abstract String tableName();

    public abstract boolean inDatabase();

    public void saveGroup() {
        if(!inDatabase() || this.dataObjects == null) return;
        String containerPrefix = this.dataContainer.tablePrefix();
        String table = containerPrefix + "_" + this.tableName();
        StorageTable storageTable = new StorageTable(table);
        StringBuilder query = new StringBuilder();
        query.append("UPDATE `").append(storageTable.getTableName()).append("` SET ");
        for(DataObject dataObject : this.dataObjects) {
            String columnName = dataObject.getColumnName();
            String value = null;
            if(dataObject.getString() != null)
                value = "'" + dataObject.getString() + "'";
            if(dataObject.getDefaultInteger() != null)
                value = dataObject.getInteger().toString();
            query.append("`").append(columnName).append("` = ").append(value).append(", ");
        }
        query.deleteCharAt(query.length() - 2);
        query.append("WHERE `UUID` = '").append(this.dataContainer.getUniqueId().toString()).append("';");
        storageTable.executeUpdate(query.toString());
    }

    public void load() {
        if(!inDatabase() || this.dataObjects == null || this.resultSet != null) return;
        String containerPrefix = this.dataContainer.tablePrefix();
        String table = containerPrefix + "_" + this.tableName();
        StorageTable storageTable = new StorageTable(table);
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        for(DataObject dataObject : this.dataObjects)
            query.append("`").append(dataObject.getColumnName()).append("`,");
        query.deleteCharAt(query.length() - 1);
        query.append(" FROM `").append(storageTable.getTableName()).append("` WHERE `UUID` = '").append(this.dataContainer.getUniqueId().toString()).append("';");
        try {
            ResultSet resultSet = storageTable.executeQuery(query.toString());
            if (resultSet == null || !resultSet.next() || resultSet.wasNull()) this.createGroup();
            else this.resultSet = resultSet;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void createGroup() {
        if(!inDatabase() || this.dataObjects == null) return;
        String containerPrefix = this.dataContainer.tablePrefix();
        String table = containerPrefix + "_" + this.tableName();
        StorageTable storageTable = new StorageTable(table);
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `").append(storageTable.getTableName()).append("` (`UUID`,");
        for(DataObject dataObject : this.dataObjects)
            query.append("`").append(dataObject.getColumnName()).append("`,");
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES (").append("'").append(this.dataContainer.getUniqueId().toString()).append("',");
        for(DataObject dataObject : this.dataObjects) {
            String value = null;
            if(dataObject.getString() != null)
                value = "'" + dataObject.getDefaultString() + "'";
            if(dataObject.getDefaultInteger() != null)
                value = dataObject.getDefaultInteger().toString();
            query.append(value).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(");");
        storageTable.executeUpdate(query.toString());
    }

    public void createTable() {
        if(!inDatabase() || this.dataObjects == null) return;
        String containerPrefix = this.dataContainer.tablePrefix();
        String table = containerPrefix + "_" + this.tableName();
        StorageTable storageTable = new StorageTable(table);
        if(storageTable.doesExist()) return;
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS `").append(storageTable.getTableName()).append("` (").append("UUID char(36) NOT NULL,");
        for(DataObject dataObject : this.dataObjects) {
            String columnName = dataObject.getColumnName();
            String dataType = null;
            String defaultValue = null;
            if(dataObject.getDefaultString() != null) {
                dataType = "varchar(255)";
                defaultValue = "'" + dataObject.getDefaultString() + "'";
            }
            if(dataObject.getDefaultInteger() != null) {
                dataType = "int";
                defaultValue = dataObject.getDefaultInteger().toString();
            }
            query.append(columnName).append(" ").append(dataType).append(" DEFAULT ").append(defaultValue).append(", ");
        }
        query.append("CONSTRAINT PK_").append(storageTable.getTableName()).append(" PRIMARY KEY (UUID))");
        storageTable.executeUpdate(query.toString());
    }
}
