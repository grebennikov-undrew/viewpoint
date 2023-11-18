package com.grebennikovas.viewpoint.datasets.column;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.parameter.ParameterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
public interface ColumnMapper {

    ColumnMapper MAPPER = Mappers.getMapper(ColumnMapper.class);

    @Mapping(target = "datasetId", source = "dataset.id")
    ColumnDto mapToColumnDto(Column column);

    Set<ColumnDto> mapToColumnsDto(Set<Column> columns);

    Column mapToColumn(ColumnDto columnDto);
}