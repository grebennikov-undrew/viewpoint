package com.grebennikovas.viewpoint.sources.connections.pgsql;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.utils.SqlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PgSqlBuilder {

    private String select;
    private String from;
    private String where;
    private String groupBy;
    private String having;
    private String orderBy;
    private String limit;
    private Map<String,String> paramValues;

    public PgSqlBuilder() {
        paramValues = new HashMap<>();
    }

    public PgSqlBuilder select(List<String> columns) {
        this.select = "SELECT " + SqlUtils.convertToString(columns);
        return this;
    }

    public PgSqlBuilder from(String table_name) {
        this.from = "FROM " + table_name;
        return this;
    }

    public PgSqlBuilder where(String conditions) {
        this.where = "WHERE " + conditions;
        return this;
    }

    public PgSqlBuilder groupBy(List<String> columns) {
        this.groupBy = "GROUP BY " + SqlUtils.convertToString(columns);
        return this;
    }

    public PgSqlBuilder having(String conditions) {
        this.having = "HAVING " + conditions;
        return this;
    }

    public PgSqlBuilder orderBy(List<String> columns, boolean desc) {
        this.orderBy = "ORDER BY " + SqlUtils.convertToString(columns);
        if (desc) this.orderBy = this.orderBy + " DESC";
        return this;
    }

    public PgSqlBuilder limit(int count) {
        this.limit = "LIMIT " + count;
        return this;
    }

    public PgSqlBuilder parameters(List<Parameter> parameters, Map<String,?> values) {
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
