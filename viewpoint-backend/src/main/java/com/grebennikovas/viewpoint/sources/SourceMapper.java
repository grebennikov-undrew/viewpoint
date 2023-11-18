package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
public interface SourceMapper {

    SourceMapper MAPPER = Mappers.getMapper(SourceMapper.class);

    Source toEntity(SourceDto sourceDto);

    @Mapping(target = "password", ignore = true)
    SourceDto toDto(Source source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "type", source = "source.type")
    @Mapping(target = "dbname", source = "source.dbname")
    SourceDto toShortDto(Source source);

}
