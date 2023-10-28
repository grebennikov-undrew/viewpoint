package com.grebennikovas.viewpoint.datasets.parameter.dto;

import com.grebennikovas.viewpoint.datasets.dto.ParameterDTO;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;

import java.util.Objects;

public class ParameterTempDTO extends ParameterDTO {
    private Long sourceId;

    public ParameterTempDTO() {
        super();
    }

    public ParameterTempDTO(Parameter parameter, Long sourceId) {
        super(parameter);
        this.sourceId = sourceId;
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
        if (!super.equals(o)) return false;

        ParameterTempDTO that = (ParameterTempDTO) o;

        return Objects.equals(sourceId, that.sourceId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        return result;
    }
}
