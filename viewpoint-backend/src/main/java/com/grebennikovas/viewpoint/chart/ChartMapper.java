package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.chart.dto.ChartRequestDto;
import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.chart.dto.ChartShortDto;
import com.grebennikovas.viewpoint.datasets.Dataset;
import com.grebennikovas.viewpoint.datasets.DatasetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChartMapper {

    ChartMapper MAPPER = Mappers.getMapper(ChartMapper.class);

    @Mapping(target = "dataset.id", source = "datasetId")
    Chart mapToChart(ChartRequestDto chartRequestDto);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "datasetId", source = "dataset.id")
    @Mapping(target = "datasetName", source = "dataset.name")
    ChartResponseDto mapToChartDto(Chart chart);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "datasetName", source = "dataset.name")
    @Mapping(target = "sourceName", source = "dataset.source.name")
    ChartShortDto mapToChartDtoShort(Chart chart);

}
