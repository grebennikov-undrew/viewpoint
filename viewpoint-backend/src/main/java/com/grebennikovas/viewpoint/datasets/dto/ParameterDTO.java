package com.grebennikovas.viewpoint.datasets.dto;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;

import java.util.Objects;

public class ParameterDTO {
    private Long id;
    private String name;
    private String type;

    public ParameterDTO() {    }

    public ParameterDTO(Parameter parameter) {
        this.id = parameter.getId();
        this.name = parameter.getName();
        this.type = parameter.getType();
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

        ParameterDTO that = (ParameterDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
