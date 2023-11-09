package com.grebennikovas.viewpoint.utils;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlBuilder {

    private String select;
    private String agg;
    private String from;
    private String where;
    private String groupBy;
    private String having;
    private String orderBy;
    private String limit;
    private Map<String,String> paramValues;

    public SqlBuilder() {
        paramValues = new HashMap<>();
    }

    public SqlBuilder select() {
        this.select = "SELECT *";
        return this;
    }

    public SqlBuilder select(List<Column> columns) {
        this.select = "SELECT " + SqlUtils.getColumnsWithLabels(columns);
        return this;
    }

    public SqlBuilder from(String table_name) {
        this.from = "FROM " + table_name;
        return this;
    }

    public SqlBuilder fromSubQuery(String subquery) {
        this.from = "FROM (" + subquery.replace(";","") + ") t";
        return this;
    }

    public SqlBuilder where(String conditions) {
        if (conditions!= null && conditions.trim().length()>0)
            this.where = "WHERE " + conditions;
        return this;
    }

    public SqlBuilder groupBy(List<Column> columns) {
        if (columns!= null && columns.size() > 0)
            this.groupBy = "GROUP BY " + SqlUtils.getColumns(columns);
        return this;
    }

    public SqlBuilder having(String conditions) {
        if (conditions!= null)
            this.having = "HAVING " + conditions;
        return this;
    }

    public SqlBuilder orderBy(List<Column> columns, Boolean desc) {
        if (columns != null && columns.size() > 0) {
            this.orderBy = "ORDER BY " + SqlUtils.getColumns(columns);
            if (desc != null && desc)
                this.orderBy = this.orderBy + " DESC";
        }
        return this;
    }

    public SqlBuilder limit(Integer count) {
        if (count!= null && count>0)
            this.limit = "LIMIT " + count;
        return this;
    }

    public SqlBuilder parameters(List<Parameter> parameters, Map<String,?> values) {
        for (Parameter parameterInfo: parameters) {
            String name = parameterInfo.getName();
            if (values.containsKey(name)){
                String value = SqlUtils.prepareParamValue(parameterInfo.getType(), values.get(name).toString());
                this.paramValues.put(name, value);
            }
            else {
                this.paramValues.put(name, "NULL");
            }
        }
        return this;
    }

    public String build() {
        StringBuilder query = new StringBuilder();

        if (select == null || from == null) throw new IllegalStateException("Invalid sql statement");

        query.append(select).append("\n");
        if (agg != null) {
            query.append(agg).append("\n");
        }

        query.append(from).append("\n");

        if (where != null) {
            query.append(where).append("\n");
        }
        if (groupBy != null) {
            query.append(groupBy).append("\n");
        }
        if (having != null) {
            query.append(having).append("\n");
        }
        if (orderBy != null) {
            query.append(orderBy).append("\n");
        }
        if (limit != null) {
            query.append(limit).append("\n");
        }

        String builtQuery = query.toString();
        // Подстановка параметров в запрос
        if (paramValues.size() > 0) {
            return SqlUtils.setParameters(builtQuery,paramValues);
        }
        return builtQuery;
    }

}
