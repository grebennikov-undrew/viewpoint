package com.grebennikovas.viewpoint.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connection")
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