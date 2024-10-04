package com.filmflicks.models;


public class ColumnMetadata {
    private String columnName;
    private String dataType;

    public ColumnMetadata(String columnName, String dataType) {
        this.columnName = columnName;
        this.dataType = dataType;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getDataType() {
        return dataType;
    }
}
