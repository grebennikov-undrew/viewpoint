package com.grebennikovas.viewpoint.datasets.parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grebennikovas.viewpoint.datasets.Dataset;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Objects;

@Entity
@Table(name = "parameters")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "dataset_id", nullable = false)
    @JsonIgnore
    private Dataset dataset;
    private String name;
    private String type;

    private String sqlQuery;

    public Parameter() {
    }

    public Parameter(Long id, Dataset dataset, String name, String type, String sqlQuery) {
        this.id = id;
        this.dataset = dataset;
        this.name = name;
        this.type = type;
        this.sqlQuery = sqlQuery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (!Objects.equals(id, parameter.id)) return false;
        if (!Objects.equals(dataset, parameter.dataset)) return false;
        if (!Objects.equals(name, parameter.name)) return false;
        if (!Objects.equals(sqlQuery, parameter.sqlQuery)) return false;
        return Objects.equals(type, parameter.type);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dataset != null ? dataset.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (type != null ? sqlQuery.hashCode() : 0);
        return result;
    }
}
