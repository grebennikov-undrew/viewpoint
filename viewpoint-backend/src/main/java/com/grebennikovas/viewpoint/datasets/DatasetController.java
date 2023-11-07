package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dataset")
@CrossOrigin(origins = "http://localhost:3000")
public class DatasetController {
    @Autowired
    DatasetService datasetService;

    // Получить все датасеты
    @GetMapping("/")
    public List<DatasetDto> findAll() {
        return datasetService.findAll();
    }

    // Сохранить/изменить датасет
    @PostMapping("/")
    public DatasetDto save(@RequestBody DatasetDto dsDTO) {
        return datasetService.save(dsDTO);
    }

    // Получить данные по датасету по id
    @GetMapping("/{id}")
    public DatasetDto getOne(@PathVariable Long id) {
        return datasetService.findById(id);
    }

    // Вернуть таблицу по запросу
    // TODO: перетащить в source controller
    @PostMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody DatasetExecDto execInfo) {
        String sqlQuery = execInfo.getSqlQuery();
        Long sourceId = execInfo.getSourceId();
        try {
            Result execResult = datasetService.execute(sqlQuery,sourceId);
            return ResponseEntity.status(HttpStatus.OK).
                    body(execResult);
        }
        catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(e.getMessage());
        }
    }
}