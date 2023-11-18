package com.grebennikovas.viewpoint.sources;

import com.grebennikovas.viewpoint.datasets.DatasetDto;
import com.grebennikovas.viewpoint.datasets.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/source")
@CrossOrigin(origins = "http://localhost:3000")
public class SourceController {
    @Autowired
    SourceService sourceService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sourceService.findAll());
    }

    @GetMapping("/{id}")
    public SourceDto findById(@PathVariable Long id) {
        return sourceService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> validateAndSave(@RequestBody SourceDto newSource) {
        try {
            SourceDto savedSource = sourceService.validateAndSave(newSource);
            return ResponseEntity.status(HttpStatus.OK).
                    body(savedSource);
        }
        catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody SourceDto newSource) {
        try {
             sourceService.validate(newSource);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }
    }




}