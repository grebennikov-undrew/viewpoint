package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceDto;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
public interface DatasetMapper {

    DatasetMapper MAPPER = Mappers.getMapper(DatasetMapper.class);

    @Mapping(target = "user", source = "userDto")
    @Mapping(target = "source", source = "sourceDto")
    @Mapping(target = "columns", source = "columnsDto")
    Dataset toEntity(DatasetDto datasetDto);

    @Mapping(target = "userDto", source = "user")
    @Mapping(target = "columnsDto", source = "columns")
    @Mapping(target = "sourceDto", source = "source", qualifiedByName = "sourceToShortDto")
    DatasetDto toDto(Dataset dataset);

    @Mapping(target = "userDto", source = "user", qualifiedByName = "onlyUsername")
    @Mapping(target = "sourceDto", source = "source", qualifiedByName = "onlySourceName")
    DatasetDto toShortDto(Dataset dataset);

    @Named("sourceToShortDto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "type")
    SourceDto sourceToShortDto(Source source);

    @Named("onlySourceName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name")
    SourceDto sourceToName(Source source);

    @Named("onlyUsername")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username")
    UserDto userToUsername(User user);

    @AfterMapping
    default void addBackReference(@MappingTarget Dataset dataset) {
        dataset.getColumns().forEach(p -> p.setDataset(dataset));
    }

}
