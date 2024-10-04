package com.filmflicks.models;

import java.util.ArrayList;
import java.util.List;

public class TableMetadata {
    private String tableName;
    private List<ColumnMetadata> columns;

    public TableMetadata(String tableName) {
        this.tableName = tableName;
        this.columns = new ArrayList<>();
    }

    public void addColumn(String columnName, String dataType) {
        this.columns.add(new ColumnMetadata(columnName, dataType));
    }

    public String getTableName() {
        return tableName;
    }

    public List<ColumnMetadata> getColumns() {
        return columns;
    }

}
