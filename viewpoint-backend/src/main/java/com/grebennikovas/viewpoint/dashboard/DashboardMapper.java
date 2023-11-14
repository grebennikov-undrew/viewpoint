package com.grebennikovas.viewpoint.dashboard;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardRequestDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardResponseDto;
import com.grebennikovas.viewpoint.dashboard.dto.DashboardShortDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface DashboardMapper {

    DashboardMapper MAPPER = Mappers.getMapper(DashboardMapper.class);

    @Mapping(target = "charts", source = "chartsId", qualifiedByName = "mapToChartList")
    Dashboard mapToDashboard(DashboardRequestDto dashboardRequestDto);

    @Mapping(target = "charts", source = "charts")
    DashboardResponseDto mapToDashboardDto(Dashboard dashboard, List<ChartResponseDto> charts);

    DashboardShortDto mapToDashboardDtoShort(Dashboard dashboard);

    @Named("mapToChartList")
    static List<Chart> mapToChartList(List<Long> chartsId) {
        return chartsId.stream()
                .map(chartId -> {
                    Chart chart = new Chart();
                    chart.setId(chartId);
                    return chart;
                })
                .collect(Collectors.toList());
    }

}
