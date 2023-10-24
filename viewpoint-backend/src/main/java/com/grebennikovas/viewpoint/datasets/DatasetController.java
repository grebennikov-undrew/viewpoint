package com.grebennikovas.viewpoint.datasets;

import com.grebennikovas.viewpoint.datasets.results.Result;
import com.grebennikovas.viewpoint.datasets.results.Row;
import com.grebennikovas.viewpoint.users.User;
import com.grebennikovas.viewpoint.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

@RestController
@RequestMapping("/api/dataset")
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
    @PostMapping("/execute")
    public Result execute(@RequestBody Long id) throws ClassNotFoundException {
        return datasetService.execute(id, null);
    }
}