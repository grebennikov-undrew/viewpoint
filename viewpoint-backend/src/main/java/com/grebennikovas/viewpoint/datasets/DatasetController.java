package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.dto.DatasetDTO;
import com.grebennikovas.viewpoint.datasets.dto.DatasetExecDTO;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
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
    public List<DatasetDTO> findAll() {
        return datasetService.findAll();
    }

    // Сохранить/изменить датасет
    @PostMapping("/")
    public DatasetDTO save(@RequestBody DatasetDTO dsDTO) {
        return datasetService.save(dsDTO);
    }

    // Получить данные по датасету по id
    @GetMapping("/{id}")
    public DatasetDTO getOne(@PathVariable Long id) {
        return datasetService.getOne(id);
    }

    // Вернуть таблицу по запросу
    @PostMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody DatasetExecDTO execInfo) {
        String sqlQuery = execInfo.getSqlQuery();
        Long sourceId = execInfo.getSourceId();
        List<Parameter> parameters = execInfo.getParameters();
        Map<String,String> paramValues = execInfo.getParamValues();
        try {
            Result execResult = datasetService.execute(sqlQuery,sourceId,parameters,paramValues);
            return ResponseEntity.status(HttpStatus.OK).
                    body(execResult);
        }
        catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(e.getMessage());
        }
    }
}