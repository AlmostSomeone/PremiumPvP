package dev.almostsomeone.premiumpvp.data;

import org.jetbrains.annotations.NotNull;

public class DataObject {

    private String columnName;

    private String defaultString;
    private Integer defaultInteger;

    private String string;
    private Integer integer;

    public DataObject(@NotNull DataGroup dataGroup, @NotNull String columnName, @NotNull String defaultValue) {
        init(dataGroup, columnName);
        this.defaultString = defaultValue;
        this.string = defaultValue;
    }

    public DataObject(@NotNull DataGroup dataGroup, @NotNull String columnName, @NotNull Integer defaultValue) {
        init(dataGroup, columnName);

        this.defaultInteger = defaultValue;
        this.integer = defaultValue;
    }

    private void init(DataGroup dataGroup, String columnName) {
        this.columnName = columnName;
        dataGroup.dataObjects.add(this);
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getDefaultString() {
        return this.defaultString;
    }

    public Integer getDefaultInteger() {
        return this.defaultInteger;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getString() {
        return this.string;
    }

    public Integer getInteger() {
        return this.integer;
    }
}
