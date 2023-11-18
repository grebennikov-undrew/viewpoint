package com.grebennikovas.viewpoint.sources;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SourceDto {

    private Long id;

    private String name;

    private String type;

    private String netloc;

    private String port;

    private String dbname;

    private String params;

    private String username;

    private String password;

}
