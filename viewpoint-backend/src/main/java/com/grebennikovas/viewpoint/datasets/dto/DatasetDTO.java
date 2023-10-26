package com.grebennikovas.viewpoint.datasets.dto;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.users.DTO.UserShortDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DatasetDTO extends DatasetShortDTO{
    private String sqlQuery;
    private UserShortDTO user;
    private Long sourceId;
    private List<ColumnDTO> columns;
    private List<ParameterDTO> parameters;
    private Date createdOn;
    private Date updatedOn;

    public DatasetDTO(Dataset ds) {
        super(ds);
        List<ColumnDTO> columns = new ArrayList<>();
        List<ParameterDTO> parameters = new ArrayList<>();
        ds.getColumns().forEach(c -> columns.add(new ColumnDTO(c)));
        ds.getParameters().forEach(p -> parameters.add(new ParameterDTO(p)));
        this.sqlQuery = ds.getSqlQuery();
        this.user = new UserShortDTO(ds.getUser().getId(),ds.getUser().getUsername());
        this.sourceId = ds.getSource().getId();
        this.columns = columns;
        this.parameters = parameters;
        this.createdOn = ds.getCreatedOn();
        this.updatedOn = ds.getUpdatedOn();
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public UserShortDTO getUser() {
        return user;
    }

    public void setUser(UserShortDTO user) {
        this.user = user;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public List<ColumnDTO> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDTO> columns) {
        this.columns = columns;
    }

    public List<ParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDTO> parameters) {
        this.parameters = parameters;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DatasetDTO that = (DatasetDTO) o;

        if (!Objects.equals(sqlQuery, that.sqlQuery)) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(sourceId, that.sourceId)) return false;
        if (!Objects.equals(columns, that.columns)) return false;
        if (!Objects.equals(parameters, that.parameters)) return false;
        if (!Objects.equals(createdOn, that.createdOn)) return false;
        return Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sqlQuery != null ? sqlQuery.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }

}
