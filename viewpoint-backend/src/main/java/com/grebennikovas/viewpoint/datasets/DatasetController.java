package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.dto.DatasetDTO;
import com.grebennikovas.viewpoint.datasets.dto.DatasetExecDTO;
import com.grebennikovas.viewpoint.datasets.parameter.Parameter;
import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dataset")
@CrossOrigin(origins = "http://localhost:3000")
public class DatasetController {
    @Autowired
    DatasetService datasetService;

    @GetMapping("/")
    public List<DatasetDTO> findAll() {
        return datasetService.findAll();
    }
    @PostMapping("/")
    public DatasetDTO save(@RequestBody DatasetDTO dsDTO) {
        return datasetService.save(dsDTO);
    }
    @GetMapping("/{id}")
    public DatasetDTO getOne(@PathVariable Long id) {
        return datasetService.getOne(id);
    }
    @PostMapping("/execute/{id}")
    public Result execute(@PathVariable Long id, @RequestBody Map<String,String> params) {
        return datasetService.execute(id, params);
    }
    @PostMapping("/execute")
    public Result execute(@RequestBody DatasetExecDTO execInfo) {
        String sqlQuery = execInfo.getSqlQuery();
        Long sourceId = execInfo.getSourceId();
        List<Parameter> parameters = execInfo.getParameters();
        Map<String,String> paramValues = execInfo.getParamValues();
        return datasetService.execute(sqlQuery,sourceId,parameters,paramValues);
    }
}