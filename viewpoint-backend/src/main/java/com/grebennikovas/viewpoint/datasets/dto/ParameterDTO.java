package com.grebennikovas.viewpoint.datasets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParameterDTO {
    private Long id;
    private String name;
    private String type;
    private String sqlQuery;
    private Long sourceId;

    public ParameterDTO() {    }

    public ParameterDTO(Parameter parameter) {
        this.id = parameter.getId();
        this.name = parameter.getName();
        this.type = parameter.getType();
        this.sqlQuery = parameter.getSqlQuery();
        this.sourceId = parameter.getDataset().getSource().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterDTO that = (ParameterDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(type, that.type)) return false;
        if (!Objects.equals(sqlQuery, that.sqlQuery)) return false;
        return Objects.equals(sourceId, that.sourceId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sqlQuery != null ? sqlQuery.hashCode() : 0);
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        return result;
    }
}
