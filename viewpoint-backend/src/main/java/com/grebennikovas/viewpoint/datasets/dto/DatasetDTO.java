package com.grebennikovas.viewpoint.datasets.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.sources.SourceShortDTO;
import com.grebennikovas.viewpoint.users.DTO.UserShortDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatasetDTO{
    private Long id;
    private String name;
    private String sqlQuery;
    private UserShortDTO user;
    private SourceShortDTO source;
    private List<ColumnDTO> columns;
    private List<ParameterDTO> parameters;
    private Date createdOn;
    private Date updatedOn;

    public DatasetDTO() {

    }

    public DatasetDTO(Dataset ds) {
        this.id = ds.getId();
        this.name = ds.getName();
        this.sqlQuery = ds.getSqlQuery();
        this.user = new UserShortDTO(ds.getUser().getId(),ds.getUser().getUsername());
        this.source = new SourceShortDTO(ds.getSource());
        this.createdOn = ds.getCreatedOn();
        this.updatedOn = ds.getUpdatedOn();
    }

    public DatasetDTO(Dataset ds, List<Column> columns, List<Parameter> params) {
        List<ColumnDTO> columnsDTO = new ArrayList<>();
        List<ParameterDTO> parametersDTO = new ArrayList<>();
        columns.forEach(c -> columnsDTO.add(new ColumnDTO(c)));
        params.forEach(p -> parametersDTO.add(new ParameterDTO(p)));
        this.id = ds.getId();
        this.name = ds.getName();
        this.sqlQuery = ds.getSqlQuery();
        this.user = new UserShortDTO(ds.getUser().getId(),ds.getUser().getUsername());
        this.source = new SourceShortDTO(ds.getSource());
        this.columns = columnsDTO;
        this.parameters = parametersDTO;
        this.createdOn = ds.getCreatedOn();
        this.updatedOn = ds.getUpdatedOn();
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

    public SourceShortDTO getSource() {
        return source;
    }

    public void setSource(SourceShortDTO source) {
        this.source = source;
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

        DatasetDTO that = (DatasetDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(sqlQuery, that.sqlQuery)) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(source, that.source)) return false;
        if (!Objects.equals(columns, that.columns)) return false;
        if (!Objects.equals(parameters, that.parameters)) return false;
        if (!Objects.equals(createdOn, that.createdOn)) return false;
        return Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sqlQuery != null ? sqlQuery.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        return result;
    }
}
