package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.column.ColumnDto;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.security.ViewPointUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/dataset")
@CrossOrigin(origins = "http://localhost:3000")
public class DatasetController {

    @Autowired
    DatasetService datasetService;

    /**
     * Получить список датасетов
     * @return список датасетов в формте коротких DTO
     * */
    @GetMapping("/")
    public ResponseEntity<List<DatasetDto>> findAll() {
        List<DatasetDto> datasets = datasetService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(datasets);
    }

    /**
     * Сохранить новый/измененный датасет
     * @param newDataset новый датасет
     * @param userDetails пользователь, внесший изменения
     * @return данные для датасета
     * */
    @PostMapping("/")
    public ResponseEntity<DatasetDto> save(@RequestBody DatasetDto newDataset,
                           @AuthenticationPrincipal ViewPointUserDetails userDetails) {
        DatasetDto savedDataset = datasetService.save(newDataset, userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(savedDataset);
    }

    /**
     * Удалить датасет по id
     * @param id id датасета
     * @return сообщение об ошибке
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws SQLException {
        datasetService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Получить данные по датасету по id
     * @param id id датасета
     * @return данные для датасета
     * */
    @GetMapping("/{id}")
    public ResponseEntity<DatasetDto> getOne(@PathVariable Long id) {
        DatasetDto foundDataset = datasetService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundDataset);
    }

    /**
     * Вернуть результаты SQL запроса
     * @param execInfo запрос и источник
     * @return данные для датасета
     * */
    @PostMapping("/execute")
    public ResponseEntity<Result> execute(@RequestBody DatasetExecDto execInfo) throws SQLException {
        String sqlQuery = execInfo.getSqlQuery();
        Long sourceId = execInfo.getSourceId();
        Result execResult = datasetService.execute(sqlQuery,sourceId);
        return ResponseEntity.status(HttpStatus.OK).
                body(execResult);
    }

    /**
     * Получить список всех колонок нескольких датасетов
     * @param datasetsId список ID датасетов
     * @return список всех колонок с их типом
     * */
    @PostMapping("/columns")
    public ResponseEntity<Set<ColumnDto>> getColumns(@RequestBody List<Long> datasetsId) {
        Set<ColumnDto> foundColumns = datasetService.getColumns(new HashSet<>(datasetsId));
        return ResponseEntity.status(HttpStatus.OK).body(foundColumns);
    }

    /**
     * Получить допустимые значения строковой колонки (для фильтра)
     * @param id ID датасета
     * @param column название колонки
     * @return данные для датасета
     * */
    @GetMapping("/{id}/{column}/values")
    public ResponseEntity<List<String>> getColumnValues(@PathVariable Long id, @PathVariable String column) throws SQLException {
        List<String> columnValues = datasetService.getColumnValues(id, column);
        return ResponseEntity.status(HttpStatus.OK).body(columnValues);
    }

    /**
     * Получить минимальное и максимальное значение столбца (для фильтра)
     * @param id ID датасета
     * @param column название колонки
     * @return данные для датасета
     * */
    @GetMapping("/{id}/{column}/bounds")
    public ResponseEntity<List<Object>> getBounds(@PathVariable Long id, @PathVariable String column) throws SQLException {
        List<Object> columnBounds = datasetService.getColumnBounds(id, column);
        return ResponseEntity.status(HttpStatus.OK).body(columnBounds);
    }
}