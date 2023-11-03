package com.grebennikovas.viewpoint.chart;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartSettings implements Serializable {
    List<String> xColumns;
    List<String> yColumns;
    String where;
    List<String> groupBy;
    String having;
    List<String> orderBy;
    Integer limit;

    public List<String> getxColumns() {
        return xColumns;
    }

    public void setxColumns(List<String> xColumns) {
        this.xColumns = xColumns;
    }

    public List<String> getyColumns() {
        return yColumns;
    }

    public void setyColumns(List<String> yColumns) {
        this.yColumns = yColumns;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public List<String> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<String> groupBy) {
        this.groupBy = groupBy;
    }

    public String getHaving() {
        return having;
    }

    public void setHaving(String having) {
        this.having = having;
    }

    public List<String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<String> orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChartSettings that = (ChartSettings) o;

        if (!Objects.equals(xColumns, that.xColumns)) return false;
        if (!Objects.equals(yColumns, that.yColumns)) return false;
        if (!Objects.equals(where, that.where)) return false;
        if (!Objects.equals(groupBy, that.groupBy)) return false;
        if (!Objects.equals(having, that.having)) return false;
        if (!Objects.equals(orderBy, that.orderBy)) return false;
        return Objects.equals(limit, that.limit);
    }

    @Override
    public int hashCode() {
        int result = xColumns != null ? xColumns.hashCode() : 0;
        result = 31 * result + (yColumns != null ? yColumns.hashCode() : 0);
        result = 31 * result + (where != null ? where.hashCode() : 0);
        result = 31 * result + (groupBy != null ? groupBy.hashCode() : 0);
        result = 31 * result + (having != null ? having.hashCode() : 0);
        result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        return result;
    }
}
