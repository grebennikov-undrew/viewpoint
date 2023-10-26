package com.grebennikovas.viewpoint.datasets.column;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grebennikovas.viewpoint.BaseEntity;
import com.grebennikovas.viewpoint.datasets.Dataset;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Objects;

@Entity
@Table(name = "columns")
public class Column extends BaseEntity {
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

    public Column() {
    }

    public Column(Long id, Dataset dataset, String name, String type) {
        this.id = id;
        this.dataset = dataset;
        this.name = name;
        this.type = type;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (!Objects.equals(id, column.id)) return false;
        if (!Objects.equals(dataset, column.dataset)) return false;
        if (!Objects.equals(name, column.name)) return false;
        return Objects.equals(type, column.type);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dataset != null ? dataset.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
