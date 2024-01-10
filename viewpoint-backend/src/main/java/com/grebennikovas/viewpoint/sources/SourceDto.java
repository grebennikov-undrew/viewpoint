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

/**
 * Dto сохранения/изменения источника данных
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SourceDto {

    /** Id источника данных */
    @Min(0)
    private Long id;

    /** Наименование источника данных */
    @NotBlank
    private String name;

    /** Тип СУБД */
    @NotBlank
    private String type;

    /** Адрес БД */
    @Pattern(regexp = "^(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.[A-Za-z]{2,})+$")
    private String netloc;

    /** Порт БД */
    @NotBlank
    private Integer port;

    /** Наименование БД */
    @NotBlank
    private String dbname;

    /** Параметры при подключении к БД */
    private String params = "";

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
