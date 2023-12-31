package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.sources.Source;
import com.grebennikovas.viewpoint.sources.SourceDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DatasetMapper {

    DatasetMapper MAPPER = Mappers.getMapper(DatasetMapper.class);

    @Mapping(target = "source", source = "sourceDto")
    @Mapping(target = "columns", source = "columnsDto")
    @Mapping(target = "user", ignore = true)
    Dataset toEntity(DatasetDto datasetDto);

    @Mapping(target = "user", source = "user.username")
    @Mapping(target = "columnsDto", source = "columns")
    @Mapping(target = "sourceDto", source = "source", qualifiedByName = "sourceToShortDto")
    DatasetDto toDto(Dataset dataset);

    @Mapping(target = "user", source = "user.username")
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

    @AfterMapping
    default void addBackReference(@MappingTarget Dataset dataset) {
        dataset.getColumns().forEach(p -> p.setDataset(dataset));
    }

}
