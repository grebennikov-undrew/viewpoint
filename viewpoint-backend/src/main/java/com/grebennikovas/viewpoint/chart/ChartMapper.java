package com.grebennikovas.viewpoint.chart;

import com.grebennikovas.viewpoint.datasets.column.Column;
import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.column.ColumnMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
public interface ChartMapper {

    ChartMapper MAPPER = Mappers.getMapper(ChartMapper.class);

    ChartDto mapToChartDto(Chart chart);

    Chart mapToChart(ChartDto chartDto);

}
