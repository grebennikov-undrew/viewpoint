package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.datasets.dto.ParameterDTO;
import com.grebennikovas.viewpoint.datasets.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/source")
@CrossOrigin(origins = "http://localhost:3000")
public class SourceController {
    @Autowired
    SourceService sourceService;

    @GetMapping("/")
    public List<Source> findAll() {
        return sourceService.findAll();
    }
    @PostMapping("/")
    public Source save(@RequestBody Source source) {
        return sourceService.check_and_save(source);
    }


}