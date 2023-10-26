package com.grebennikovas.viewpoint.datasets.dto;

import com.grebennikovas.viewpoint.datasets.Dataset;

import java.util.Objects;

public class DatasetShortDTO {
    private Long id;
    private String name;

    public DatasetShortDTO() { }

    public DatasetShortDTO(Dataset ds) {
        this.id = ds.getId();
        this.name = ds.getName();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatasetShortDTO that = (DatasetShortDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
