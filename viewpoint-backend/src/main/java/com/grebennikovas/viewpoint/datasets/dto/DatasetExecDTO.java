package com.grebennikovas.viewpoint.datasets.dto;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

//String query, Long sourceId, List<Parameter> parameters, Map<String,String> paramValues
public class DatasetExecDTO {
    private String sqlQuery;
    private Long sourceId;
    private List<Parameter> parameters;
    private Map<String,String> paramValues;

    public DatasetExecDTO() {
    }

    public DatasetExecDTO(String query, Long sourceId, List<Parameter> parameters, Map<String, String> paramValues) {
        this.sqlQuery = query;
        this.sourceId = sourceId;
        this.parameters = parameters;
        this.paramValues = paramValues;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String query) {
        this.sqlQuery = query;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getParamValues() {
        return paramValues;
    }

    public void setParamValues(Map<String, String> paramValues) {
        this.paramValues = paramValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatasetExecDTO that = (DatasetExecDTO) o;

        if (!Objects.equals(sqlQuery, that.sqlQuery)) return false;
        if (!Objects.equals(sourceId, that.sourceId)) return false;
        if (!Objects.equals(parameters, that.parameters)) return false;
        return Objects.equals(paramValues, that.paramValues);
    }

    @Override
    public int hashCode() {
        int result = sqlQuery != null ? sqlQuery.hashCode() : 0;
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (paramValues != null ? paramValues.hashCode() : 0);
        return result;
    }
}
