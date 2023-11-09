package com.grebennikovas.viewpoint.datasets.parameter;

import com.grebennikovas.viewpoint.datasets.column.ColumnMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
public interface ParameterMapper {

    ParameterMapper MAPPER = Mappers.getMapper(ParameterMapper.class);

    ParameterDto mapToParameterDto(Parameter parameter);

    Parameter mapToParameter(ParameterDto parameterDto);

}