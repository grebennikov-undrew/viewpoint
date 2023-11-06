package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.DatasetDto;
import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.column.ColumnMapper;
import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceDto;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
public interface ChartMapper {

    ChartMapper MAPPER = Mappers.getMapper(ChartMapper.class);

    @Mapping(target = "dataset", source = "datasetDto")
    @Mapping(target = "user", source = "userDto")
    @Mapping(target = "dataset.source", source = "datasetDto.sourceDto")
    @Mapping(target = "dataset.parameters", source = "datasetDto.parametersDto")
    Chart mapToChart(ChartDto chartDto);

    @Mapping(target = "userDto", source = "user")
    @Mapping(target = "datasetDto", source = "dataset", qualifiedByName = "datasetToDto")
    ChartDto mapToChartDto(Chart chart);

    @Named("datasetToDto")
    @Mapping(target = "userDto", source = "user")
    @Mapping(target = "parametersDto", source = "parameters")
    @Mapping(target = "sourceDto", source = "source", qualifiedByName = "sourceToShortDto")
    DatasetDto DataetToDto(Dataset dataset);

    @Named("sourceToShortDto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "type")
    SourceDto sourceToShortDto(Source source);




}
