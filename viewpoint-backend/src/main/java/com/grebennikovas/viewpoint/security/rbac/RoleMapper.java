package com.grebennikovas.viewpoint.security.rbac;

import com.grebennikovas.viewpoint.chart.Chart;
import com.grebennikovas.viewpoint.chart.ChartMapper;
import com.grebennikovas.viewpoint.chart.dto.ChartRequestDto;
import com.grebennikovas.viewpoint.chart.dto.ChartResponseDto;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleDto;
import com.grebennikovas.viewpoint.security.rbac.dto.RoleShortDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    Role mapToRole(RoleDto roleDto);

    @Named("full")
    RoleDto mapToRoleDto(Role role);

    @Named("short")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "name")
    RoleShortDto mapToShortDto(Role role);

}
