package com.grebennikovas.viewpoint.utils;

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

    public SqlBuilder select() {
        this.select = "SELECT *";
        return this;
    }

    public SqlBuilder select(List<Alias> aliases) {
        this.select = "SELECT " + SqlUtils.getColumnsWithLabels(aliases);
        return this;
    }

    public SqlBuilder selectDistinct(List<Alias> aliases) {
        this.select = "SELECT DISTINCT " + SqlUtils.getColumnsWithLabels(aliases);
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

    public SqlBuilder groupBy(List<Alias> aliases) {
        if (aliases != null && aliases.size() > 0)
            this.groupBy = "GROUP BY " + SqlUtils.getColumns(aliases);
        return this;
    }

    public SqlBuilder having(String conditions) {
        if (conditions!= null)
            this.having = "HAVING " + conditions;
        return this;
    }

    public SqlBuilder orderBy(List<Alias> aliases, Boolean desc) {
        if (aliases != null && aliases.size() > 0) {
            this.orderBy = "ORDER BY " + SqlUtils.getColumns(aliases);
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
        return query.toString();
    }

}
