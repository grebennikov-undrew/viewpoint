package com.grebennikovas.viewpoint.datasets;

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
    public List<Dataset> findAll() {
        return datasetService.findAll();
    }
    @PostMapping("/")
    public Dataset save(@RequestBody Dataset dataset) {
        return datasetService.save(dataset);
    }
    @GetMapping("/{id}")
    public Dataset getOne(@PathVariable Long id) {
        return datasetService.getOne(id);
    }
    @PostMapping("/execute/{id}")
    public Result execute(@PathVariable Long id, @RequestBody Map<String,String> params) {
        return datasetService.execute(id, params);
    }
}