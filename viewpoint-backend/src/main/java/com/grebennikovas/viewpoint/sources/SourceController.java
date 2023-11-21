package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.datasets.DatasetDto;
import com.grebennikovas.viewpoint.datasets.results.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/source")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="Модуль подключений к внешним источникам данных")
public class SourceController {

    @Autowired
    SourceService sourceService;

    /**
     * Получить список источников данных
     * @return список источников в формте коротких DTO
     * */
    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ SOURCE LIST')")
    @Operation(summary = "Все подключенные источники данных")
    public ResponseEntity<List<SourceDto>> findAll() {
        List<SourceDto> foundSources = sourceService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundSources);
    }

    /**
     * Получить данные по источнику данных по id
     * @param id id источника
     * @return информация о подключении
     * */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ SOURCE')")
    @Operation(summary = "Детализация источника данных")
    public ResponseEntity<SourceDto> findById(@PathVariable Long id) {
        SourceDto foundSource = sourceService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundSource);
    }

    /**
     * Проверить и сохранить новое подключение
     * @param newSource новое подключение
     * @return информация о сохраненном подключении
     * */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('EDIT SOURCE')")
    @Operation(summary = "Проверить и сохранить новое подключение")
    public ResponseEntity<SourceDto> validateAndSave(@RequestBody SourceDto newSource) throws SQLException {
        SourceDto savedSource = sourceService.validateAndSave(newSource);
        return ResponseEntity.status(HttpStatus.OK).
                body(savedSource);
    }

    /**
     * Проверить подключение
     * @param newSource настройки подключения для проверки
     * */
    @PostMapping("/validate")
    @PreAuthorize("hasAuthority('EDIT SOURCE')")
    @Operation(summary = "Проверить подключение")
    public ResponseEntity<?> validate(@RequestBody SourceDto newSource) throws SQLException {
        sourceService.validate(newSource);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Удалить источник данных по id
     * @param id id источника
     * @return сообщение об ошибке
     * */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE SOURCE')")
    @Operation(summary = "Удалить подключение")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws SQLException {
        sourceService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}