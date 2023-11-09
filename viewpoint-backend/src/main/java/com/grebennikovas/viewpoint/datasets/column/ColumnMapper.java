package com.grebennikovas.viewpoint.datasets.column;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
public interface ColumnMapper {

    ColumnMapper MAPPER = Mappers.getMapper(ColumnMapper.class);

    ColumnDto mapToColumnDto(Column column);

    Column mapToColumn(ColumnDto columnDto);
}