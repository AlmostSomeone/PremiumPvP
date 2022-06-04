package dev.almostsomeone.premiumpvp.data;

import javax.annotation.Nonnull;

public class DataObject {

    private String columnName;

    private String defaultString;
    private Integer defaultInteger;

    private String string;
    private Integer integer;

    public DataObject(@Nonnull DataGroup dataGroup, @Nonnull String columnName, @Nonnull String defaultValue) {
        init(dataGroup, columnName);
        defaultString = defaultValue;
        string = defaultValue;
    }

    public DataObject(@Nonnull DataGroup dataGroup, @Nonnull String columnName, @Nonnull Integer defaultValue) {
        init(dataGroup, columnName);

        defaultInteger = defaultValue;
        integer = defaultValue;
    }

    private void init(DataGroup dataGroup, String columnName) {
        this.columnName = columnName;
        dataGroup.dataObjects.add(this);
    }

    public String getColumnName() {
        return columnName;
    }

    public String getDefaultString() {
        return defaultString;
    }

    public Integer getDefaultInteger() {
        return defaultInteger;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public Integer getInteger() {
        return integer;
    }
}
