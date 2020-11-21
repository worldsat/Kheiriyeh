package com.atrinfanavaran.kheiriyeh.Kernel.Domain;


import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;

public class FnValidColumnList extends BaseDomain {

    private String RowNumber;
    private String SchemaName;
    private String TableName;
    private String ColumnName;
    private String IsNullable;
    private String DataType;
    private String IsPrimaryKey;
    private String IsIdentity;
    private String SoftwareOperationId;
    private String Condition;
    private String DashboardTitleId;
    private String DashboardProcessStatusId;
    private String EditableColumns;
    private String DeletableRows;
    private String ValidColumnListId;
    private String UserId;


    public FnValidColumnList() {
        setApiAddresss("api/pgFnValidColumnList");
        setTableName("t_ValidColumnList");
    }

    public String getRowNumber() {
        return RowNumber;
    }

    public String getValidColumnListId() {
        return ValidColumnListId;
    }

    public void setValidColumnListId(String validColumnListId) {
        ValidColumnListId = validColumnListId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setRowNumber(String rowNumber) {
        RowNumber = rowNumber;
    }

    public String getSchemaName() {
        return SchemaName;
    }

    public void setSchemaName(String schemaName) {
        SchemaName = schemaName;
    }

    @Override
    public String getTableName() {
        return TableName;
    }

    @Override
    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String columnName) {
        ColumnName = columnName;
    }

    public String getIsNullable() {
        return IsNullable;
    }

    public void setIsNullable(String isNullable) {
        IsNullable = isNullable;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getIsPrimaryKey() {
        return IsPrimaryKey;
    }

    public void setIsPrimaryKey(String isPrimaryKey) {
        IsPrimaryKey = isPrimaryKey;
    }

    public String getIsIdentity() {
        return IsIdentity;
    }

    public void setIsIdentity(String isIdentity) {
        IsIdentity = isIdentity;
    }

    public String getSoftwareOperationId() {
        return SoftwareOperationId;
    }

    public void setSoftwareOperationId(String softwareOperationId) {
        SoftwareOperationId = softwareOperationId;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getDashboardTitleId() {
        return DashboardTitleId;
    }

    public void setDashboardTitleId(String dashboardTitleId) {
        DashboardTitleId = dashboardTitleId;
    }

    public String getDashboardProcessStatusId() {
        return DashboardProcessStatusId;
    }

    public void setDashboardProcessStatusId(String dashboardProcessStatusId) {
        DashboardProcessStatusId = dashboardProcessStatusId;
    }

    public String getEditableColumns() {
        return EditableColumns;
    }

    public void setEditableColumns(String editableColumns) {
        EditableColumns = editableColumns;
    }

    public String getDeletableRows() {
        return DeletableRows;
    }

    public void setDeletableRows(String deletableRows) {
        DeletableRows = deletableRows;
    }
}