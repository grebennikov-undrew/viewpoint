package com.grebennikovas.viewpoint.sources;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SourceDto {

    @Min(0)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @Pattern(regexp = "^(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.[A-Za-z]{2,})+$")
    private String netloc;

    @NotBlank
    private Integer port;

    @NotBlank
    private String dbname;

    private String params = "";

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
